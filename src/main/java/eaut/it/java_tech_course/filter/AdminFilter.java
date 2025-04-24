package eaut.it.java_tech_course.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = {"/employees?action=new", "/employees?action=edit", "/employees?action=delete", "/employees?action=save"})
public class AdminFilter implements Filter {
    @SuppressWarnings("unchecked")
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        List<String> roles = (List<String>) httpRequest.getSession().getAttribute("roles");
        if (roles != null && roles.contains("admin")) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/WEB-INF/views/accessDenied.jsp");
        }
    }
}