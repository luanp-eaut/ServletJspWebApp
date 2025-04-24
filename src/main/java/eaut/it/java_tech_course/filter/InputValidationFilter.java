package eaut.it.java_tech_course.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/employees?action=save")
public class InputValidationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String fullName = httpRequest.getParameter("fullName");
        String salaryStr = httpRequest.getParameter("salary");

        if (fullName == null || fullName.trim().isEmpty()) {
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Full name is required.");
            return;
        }

        try {
            double salary = Double.parseDouble(salaryStr);
            if (salary < 0) {
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Salary cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid salary format.");
            return;
        }

        chain.doFilter(request, response);
    }
}
