package eaut.it.java_tech_course.controller;

import java.io.IOException;

import eaut.it.java_tech_course.model.LoginResult;
import eaut.it.java_tech_course.service.UserService;
import eaut.it.java_tech_course.utils.PasswordUtil;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/login", "/login-error" })
public class LoginServlet extends HttpServlet {
	@Inject
	private UserService userDAO;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Hashed Password: "+PasswordUtil.hashPassword("user123"));
		String servletPath = request.getServletPath();
		if ("/login-error".equals(servletPath)) {
			request.setAttribute("error", "Invalid username or password.");
		}
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		LoginResult loginResult = userDAO.login(username, password);
		if (loginResult != null) {
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("roles", loginResult.getRoles());
			response.sendRedirect(request.getContextPath() + "/home");
		} else {
			response.sendRedirect(request.getContextPath() + "/login-error");
		}
	}
}