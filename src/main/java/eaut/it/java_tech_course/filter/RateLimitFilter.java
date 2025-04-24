package eaut.it.java_tech_course.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class RateLimitFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(RateLimitFilter.class.getName());
    private final Map<String, RateLimit> rateLimits = new ConcurrentHashMap<>();
    private int maxRequests;
    private long duration;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Properties props = new Properties();
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
            maxRequests = Integer.parseInt(props.getProperty("rate.limit.requests", "50"));
            duration = Long.parseLong(props.getProperty("rate.limit.duration.seconds", "60")) * 1000;
        } catch (Exception e) {
            LOGGER.severe("Failed to load rate limit config: " + e.getMessage());
            maxRequests = 50;
            duration = 60_000;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String ipAddress = httpRequest.getRemoteAddr();
        RateLimit rateLimit = rateLimits.computeIfAbsent(ipAddress, k -> new RateLimit());

        synchronized (rateLimit) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - rateLimit.lastReset > duration) {
                rateLimit.count.set(0);
                rateLimit.lastReset = currentTime;
            }

            if (rateLimit.count.incrementAndGet() > maxRequests) {
                LOGGER.warning("Rate limit exceeded for IP: " + ipAddress);
                httpResponse.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                httpResponse.getWriter().write("Too many requests. Please try again later.");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private static class RateLimit {
        AtomicInteger count = new AtomicInteger(0);
        long lastReset = System.currentTimeMillis();
    }
}
