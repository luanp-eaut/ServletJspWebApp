package eaut.it.java_tech_course.service;

import java.util.List;

import eaut.it.java_tech_course.model.Employee;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Named
@ApplicationScoped
@Transactional
public class EmployeeService {
    @PersistenceContext(unitName = "EmployeePU")
    private EntityManager em;

    public List<Employee> getAllEmployees() {
        return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
    }

    public Employee getEmployeeById(Long id) {
        return em.find(Employee.class, id);
    }
 
    public void saveEmployee(Employee employee) {
        if (employee.getId() == null) {
            em.persist(employee);
        } else {
            em.merge(employee);
        }
    }

    public void deleteEmployee(Long id) {
        Employee employee = em.find(Employee.class, id);
        if (employee != null) {
            em.remove(employee);
        }
    }
}
