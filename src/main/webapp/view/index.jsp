<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored = "false" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Main page</title>
</head>
<body>
${sessionCounter}
<c:if test="${error != null}">
    Error is happened ${error}
</c:if>
<security:authorize access="hasRole('ADMIN')">
    <a href="${pageContext.request.contextPath}/users">Users</a>
</security:authorize>
<security:authorize access="isAuthenticated()">
    Hello, <security:authentication property="name"/>!
    <a href="${pageContext.request.contextPath}/cabinet">Cabinet</a>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</security:authorize>
<security:authorize access="!isAuthenticated()">
        <a href="submit">Go to submit page</a>
        <a href="login">Go to login page</a>
</security:authorize>
</body>
</html>
