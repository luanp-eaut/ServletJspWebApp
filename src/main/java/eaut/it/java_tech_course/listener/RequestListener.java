package eaut.it.java_tech_course.listener;

import java.util.logging.Logger;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

@WebListener
public class RequestListener implements ServletRequestListener {
    private static final Logger LOGGER = Logger.getLogger(RequestListener.class.getName());

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        String url = request.getRequestURI();
        String userAgent = request.getHeader("User-Agent");
        String referer = request.getHeader("Referer");

        LOGGER.info(String.format("Request initialized: %s | User-Agent: %s | Referer: %s", url, userAgent, referer));

        request.getSession().setAttribute("clientIp", request.getRemoteAddr());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        String url = request.getRequestURI();
        LOGGER.info("Request destroyed: " + url);
    }
}