package eaut.it.java_tech_course.service;

import java.util.List;

import eaut.it.java_tech_course.model.LoginResult;
import eaut.it.java_tech_course.model.User;
import eaut.it.java_tech_course.utils.PasswordUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Named
@ApplicationScoped
@Transactional
public class UserService {
    @PersistenceContext(unitName = "EmployeePU")
    private EntityManager em;

    public User findUserByUsername(String username) {
        return em.find(User.class, username);
    }
    
    public LoginResult login(String username, String password) {
		User user = findUserByUsername(username);
		LoginResult loginResult = new LoginResult();
		System.out.println("User: " + username + " Password: " + password);
		System.out.println("User Object: " + user);
        if (user != null && user.isEnabled() && PasswordUtil.checkPassword(password, user.getPassword())) {
        	loginResult.setUserName(user.getUsername());
            List<String> roles = em.createQuery("SELECT ur.role FROM UserRole ur WHERE ur.user.username = :username", String.class)
                    .setParameter("username", username)
                    .getResultList();
            loginResult.setRoles(roles);
            System.out.println("Login successful for user: " + username);
        } else {
        	System.out.println("Login failed for user: " + username);
            loginResult=null;
        }
		return loginResult;
	}

	public void saveUser(User user) {
		em.getTransaction().begin();
		if (user.getUsername() == null) {
			em.persist(user);
		} else {
			em.merge(user);
		}
		em.getTransaction().commit();
	}

	public void deleteUser(String username) {
		User user = em.find(User.class, username);
		if (user != null) {
			em.remove(user);
		}
	}
}
