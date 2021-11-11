<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
    <title>Courses</title>
    </head>
    <body>
    <h2>Columns</h2>
    <ul id="reviews">
    <c:forEach var="review" items ="${requestScope.reviews}">
    <li id="review-user_id">${review.user_id}</li>
    <li id="review-course_id">${review.course_id}</li>
    <li id="review-grade">${review.grade}</li>
    <li id="review-review">${review.review}</li>
    </c:forEach>
    </ul>
    </body>
</html>