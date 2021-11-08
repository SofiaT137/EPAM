<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
    <title>Courses</title>
    </head>
    <body>
    <h2>Columns</h2>
    <ul id="courses">
    <c:forEach var="course" items ="${requestScope.courses}">
    <li id="course-name">${course.name}</li>
    </c:forEach>
    </ul>
    </body>
</html>