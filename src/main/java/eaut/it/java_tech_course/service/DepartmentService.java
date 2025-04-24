package eaut.it.java_tech_course.service;

import java.util.List;

import eaut.it.java_tech_course.model.Department;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Named
@ApplicationScoped
public class DepartmentService {
    @PersistenceContext(unitName = "EmployeePU")
    private EntityManager em;

    public List<Department> getAllDepartments() {
        return em.createQuery("SELECT d FROM Department d", Department.class).getResultList();
    }

    public Department getDepartmentById(Long id) {
        return em.find(Department.class, id);
    }
}
