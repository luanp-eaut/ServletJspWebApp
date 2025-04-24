package eaut.it.java_tech_course.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Logger;

import eaut.it.java_tech_course.model.Employee;
import eaut.it.java_tech_course.service.DepartmentService;
import eaut.it.java_tech_course.service.EmployeeService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/employees/*")
public class EmployeeServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(EmployeeServlet.class.getName());

    @Inject
    private EmployeeService employeeDAO;

    @Inject
    private DepartmentService departmentDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteEmployee(request, response);
                break;
            case "searchForm": // Mới: Hiển thị form tìm kiếm
                showSearchForm(request, response);
                break;
            case "search":
                searchEmployees(request, response);
                break;
            default:
                listEmployees(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "save":
                saveEmployee(request, response);
                break;
            case "search": // Xử lý tìm kiếm từ form
                searchEmployees(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/employees");
                break;
        }
    }

    private void listEmployees(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("employees", employeeDAO.getAllEmployees());
        request.getRequestDispatcher("/WEB-INF/views/employeeList.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("departments", departmentDAO.getAllDepartments());
        request.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Employee employee = employeeDAO.getEmployeeById(id);
        request.setAttribute("employee", employee);
        request.setAttribute("departments", departmentDAO.getAllDepartments());
        request.getRequestDispatcher("/WEB-INF/views/employeeForm.jsp").forward(request, response);
    }

    private void saveEmployee(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Employee employee = new Employee();
        if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
            employee.setId(Long.parseLong(request.getParameter("id")));
        }
        employee.setFullName(request.getParameter("fullName"));
        employee.setBirthDate(LocalDate.parse(request.getParameter("birthDate")));
        Long departmentId = Long.parseLong(request.getParameter("departmentId"));
        employee.setDepartment(departmentDAO.getDepartmentById(departmentId));
        employee.setSalary(Double.parseDouble(request.getParameter("salary")));
        employeeDAO.saveEmployee(employee);
        response.sendRedirect(request.getContextPath() + "/employees");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        employeeDAO.deleteEmployee(id);
        response.sendRedirect(request.getContextPath() + "/employees");
    }

    private void showSearchForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/searchEmployee.jsp").forward(request, response);
    }

    private void searchEmployees(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        if (keyword == null || keyword.trim().isEmpty()) {
            request.setAttribute("employees", employeeDAO.getAllEmployees());
        } else {
            var employees = employeeDAO.getAllEmployees().stream()
                    .filter(e -> e.getFullName().toLowerCase().contains(keyword.toLowerCase()))
                    .toList();
            request.setAttribute("employees", employees);
            request.setAttribute("keyword", keyword); // Giữ lại từ khóa để hiển thị trong form
        }
        request.getRequestDispatcher("/WEB-INF/views/employeeList.jsp").forward(request, response);
    }
}