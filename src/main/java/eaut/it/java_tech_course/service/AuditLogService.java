package eaut.it.java_tech_course.service;

import eaut.it.java_tech_course.model.AuditLog;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Named
@ApplicationScoped
@Transactional
public class AuditLogService {
	@PersistenceContext(unitName = "EmployeePU")
    private EntityManager em;

    public void saveAuditLog(AuditLog log) {
        em.persist(log);
    }
}
