<%--
  Created by IntelliJ IDEA.
  User: vielen
  Date: 5/4/20
  Time: 5:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Updating page</title>
</head>
<body>
${user}
${user.username}
${user.password}
<form action="${pageContext.request.contextPath}/updating/${user.id}" method="post">
    <input type="text" name="username" placeholder="Insert new username">
    <input type="text" name="password" placeholder="Insert new password">
    <input type="submit" value="Submit">
</form>
</body>
</html>
