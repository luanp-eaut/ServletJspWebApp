package eaut.it.java_tech_course.listener;

import java.util.List;
import java.util.logging.Logger;

import eaut.it.java_tech_course.model.Department;
import eaut.it.java_tech_course.service.DepartmentService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(AppContextListener.class.getName());

    @Inject
    private DepartmentService departmentService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Application starting...");

        ServletContext context = sce.getServletContext();
        List<Department> departments = departmentService.getAllDepartments();
        context.setAttribute("departments", departments);
        LOGGER.info("Loaded " + departments.size() + " departments into context.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Application shutting down...");
    }
}
