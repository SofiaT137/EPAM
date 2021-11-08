<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
    <title>Users</title>
    </head>
    <body>
    <h2>Columns</h2>
    <ul id="users">
    <c:forEach var="user" items ="${requestScope.users}">
    <li id="user-first_name">${user.first_name}</li>
    <li id="user-last_name">${user.last_name}</li>
    </c:forEach>
    </ul>
    </body>
</html>