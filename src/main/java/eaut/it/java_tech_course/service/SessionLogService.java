package eaut.it.java_tech_course.service;

import eaut.it.java_tech_course.model.SessionLog;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Named
@ApplicationScoped
@Transactional
public class SessionLogService {
    @PersistenceContext(unitName = "EmployeePU")
    private EntityManager em;

    public void saveSessionLog(SessionLog log) {
        em.persist(log);
    }

    public void updateSessionLog(SessionLog log) {
        em.merge(log);
    }
}
