package eaut.it.java_tech_course.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = { "/employees/*", "/home" })
public class AuthFilter implements Filter {
	private static final List<String> ADMIN_ACTIONS = Arrays.asList("new", "edit", "delete", "save");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// Kiểm tra đăng nhập
		if (httpRequest.getSession().getAttribute("username") == null) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
			return;
		}

		// Kiểm tra vai trò admin cho các hành động CRUD
		String action = httpRequest.getParameter("action");
		if (action != null && ADMIN_ACTIONS.contains(action)) {
			List<String> roles = (List<String>) httpRequest.getSession().getAttribute("roles");
			if (roles == null || !roles.contains("admin")) {
				httpRequest.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(request, response);
				return;
			}
		}

		// Cho phép tiếp tục nếu đã đăng nhập và không phải hành động CRUD hoặc có vai
		// trò admin
		chain.doFilter(request, response);
	}
}
