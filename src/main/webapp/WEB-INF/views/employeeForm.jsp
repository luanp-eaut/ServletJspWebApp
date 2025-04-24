<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Employee Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/home">Employee Management</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/employees">Employee List</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/employees?action=searchForm">Search Employees</a>
                    </li>
                    <c:if test="${sessionScope.roles.contains('admin')}">
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/employees?action=new">Add Employee</a>
                        </li>
                    </c:if>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <h2>${employee == null ? 'Add New Employee' : 'Edit Employee'}</h2>
        <form action="${pageContext.request.contextPath}/employees" method="post" class="mt-3">
            <input type="hidden" name="action" value="save">
            <input type="hidden" name="id" value="${employee != null ? employee.id : ''}">
            <div class="mb-3">
                <label for="fullName" class="form-label">Full Name:</label>
                <input type="text" class="form-control" id="fullName" name="fullName" value="${employee != null ? employee.fullName : ''}" required>
            </div>
            <div class="mb-3">
                <label for="birthDate" class="form-label">Birth Date:</label>
                <input type="date" class="form-control" id="birthDate" name="birthDate" value="${employee != null ? employee.birthDate : ''}" required>
            </div>
            <div class="mb-3">
                <label for="departmentId" class="form-label">Department:</label>
                <select class="form-select" id="departmentId" name="departmentId" required>
                    <c:forEach var="department" items="${departments}">
                        <option value="${department.id}" ${employee != null && employee.department.id == department.id ? 'selected' : ''}>
                            ${department.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="salary" class="form-label">Salary:</label>
                <input type="number" step="0.01" class="form-control" id="salary" name="salary" value="${employee != null ? employee.salary : ''}" required>
            </div>
            <button type="submit" class="btn btn-primary">Save</button>
            <a href="${pageContext.request.contextPath}/employees" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>