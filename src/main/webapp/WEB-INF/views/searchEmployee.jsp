<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Search Employees</title>
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
                        <a class="nav-link active" href="${pageContext.request.contextPath}/employees?action=searchForm">Search Employees</a>
                    </li>
                    <c:if test="${sessionScope.roles.contains('admin')}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/employees?action=new">Add Employee</a>
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
        <h2>Search Employees</h2>
        <form action="${pageContext.request.contextPath}/employees" method="post" class="mt-3">
            <input type="hidden" name="action" value="search">
            <div class="mb-3">
                <label for="keyword" class="form-label">Search by Name:</label>
                <input type="text" class="form-control" id="keyword" name="keyword" value="${keyword}" placeholder="Enter employee name">
            </div>
            <button type="submit" class="btn btn-primary">Search</button>
            <a href="${pageContext.request.contextPath}/employees" class="btn btn-secondary">Back to List</a>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>