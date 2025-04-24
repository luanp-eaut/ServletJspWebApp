package eaut.it.java_tech_course.listener;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import eaut.it.java_tech_course.model.SessionLog;
import eaut.it.java_tech_course.service.SessionLogService;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final Logger LOGGER = Logger.getLogger(SessionListener.class.getName());

    @Inject
    private SessionLogService sessionLogService;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        String sessionId = session.getId();
        String username = (String) session.getAttribute("username");
        if (username == null) {
            username = "Anonymous";
        }
        String ipAddress = (String) session.getAttribute("clientIp");

        SessionLog log = new SessionLog();
        log.setSessionId(sessionId);
        log.setUsername(username);
        log.setIpAddress(ipAddress);
        log.setCreatedAt(LocalDateTime.now());
        sessionLogService.saveSessionLog(log);

        LOGGER.info("Session created: " + sessionId + " for user: " + username);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        String sessionId = session.getId();
        String username = (String) session.getAttribute("username");
        if (username == null) {
            username = "Anonymous";
        }
        String ipAddress = (String) session.getAttribute("clientIp");

        SessionLog log = new SessionLog();
        log.setSessionId(sessionId);
        log.setUsername(username);
        log.setIpAddress(ipAddress);
        log.setCreatedAt(LocalDateTime.now()); // Giả sử lấy từ DB nếu cần
        log.setDestroyedAt(LocalDateTime.now());
        sessionLogService.updateSessionLog(log);

        LOGGER.info("Session destroyed: " + sessionId + " for user: " + username);
    }
}
