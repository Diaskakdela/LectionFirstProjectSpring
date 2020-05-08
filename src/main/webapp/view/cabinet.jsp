<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: vielen
  Date: 4/27/20
  Time: 4:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Cabinet</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/logout">Logout</a>
<a href="${pageContext.request.contextPath}/">Main page</a>
    <div>
        Your ID is: ${user.userDetailsId}
    </div>
    <div>
        Your username is: ${user.username}
    </div>
<form action="${pageContext.request.contextPath}/update/${user.userDetailsId}" method="post">
    <input type="text" name="username" placeholder="Update your username"/>
    <input type="password" name="password" placeholder="Update your password"/>
    <input type="submit" value="Update">
</form>
</body>
</html>
