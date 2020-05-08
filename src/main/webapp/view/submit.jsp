<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored = "false" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Main page</title>
</head>
<body>
${value}
<a href="index.jsp">Main page</a>
    <form action="submit" method="post" enctype="multipart/form-data">
        <input type="radio" name="role" value="user" checked="checked">User
        <input type="radio" name="role" value="admin">Admin
        <input type="text" name="username" placeholder="Insert your username"/>
        <input type="password" name="password" placeholder="Insert your password" />
<%--        <input type="file" name="file-name" />--%>
        <input type="submit" name="submit"/>
    </form>
</body>
</html>
