package eaut.it.java_tech_course.filter;

import java.io.IOException;
import java.time.LocalDateTime;

import eaut.it.java_tech_course.model.AuditLog;
import eaut.it.java_tech_course.service.AuditLogService;
import jakarta.inject.Inject;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

@WebFilter(urlPatterns = {"/employees/*"})
public class AuditFilter implements Filter {
    @Inject
    private AuditLogService auditLogDAO;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String action = httpRequest.getParameter("action");
        String username = httpRequest.getSession().getAttribute("username") != null 
            ? (String) httpRequest.getSession().getAttribute("username") : "Anonymous";
        Long entityId = null;

        if ("save".equals(action) && httpRequest.getParameter("id") != null && !httpRequest.getParameter("id").isEmpty()) {
            entityId = Long.parseLong(httpRequest.getParameter("id"));
        } else if ("delete".equals(action)) {
            entityId = Long.parseLong(httpRequest.getParameter("id"));
        }

        chain.doFilter(request, response);

        if (username != null && ("save".equals(action) || "delete".equals(action))) {
            AuditLog log = new AuditLog();
            log.setUsername(username);
            log.setAction(action.equals("save") ? "SAVE_EMPLOYEE" : "DELETE_EMPLOYEE");
            log.setEntityId(entityId);
            log.setTimestamp(LocalDateTime.now());
            auditLogDAO.saveAuditLog(log);
        }
    }
}