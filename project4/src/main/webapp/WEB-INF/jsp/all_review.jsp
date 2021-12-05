<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="allTheUniversityReviews" var="allTheUniversityReviews"/>
<fmt:message bundle="${loc}" key="noReviews" var="noReviews"/>
<fmt:message bundle="${loc}" key="reviewId" var="reviewId"/>
<fmt:message bundle="${loc}" key="userId" var="userId"/>
<fmt:message bundle="${loc}" key="courseName" var="courseName"/>
<fmt:message bundle="${loc}" key="Grade" var="Grade"/>
<fmt:message bundle="${loc}" key="Review" var="Review"/>
<fmt:message bundle="${loc}" key="getBack" var="getBack"/>

<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
    <style>
        <%@include file="/WEB-INF/css/tables.css"%><%@include file="/WEB-INF/css/labels_buttons.css"%>
    </style>
</head>

<body>
    <div class="wrapper">
    <h2>${allTheUniversityReviews}</h2>
    <c:choose>
        <c:when test="${all_reviews.size() eq 0}">
            <p>${noReviews}</p>
            </c:when>
        <c:otherwise>
            <table border="1" table style="width: 33%" style="text-align: center">
                <thead>
                    <tr>
                        <th>${reviewId}</th>
                        <th>${userId}</th>
                        <th>${courseName}</th>
                        <th>${Grade}</th>
                        <th>${Review}</th>
                    </tr>
                </thead>
                <c:forEach items="${requestScope.all_reviews}" var="review">
                    <tbody>
                        <tr>
                            <td>
                                <c:out value="${review.id}" />
                            </td>
                            <td>
                                <c:out value="${review.user_id}" />
                            </td>
                            <td>
                                <c:out value="${review.course_name}" />
                            </td>
                            <td>
                                <c:out value="${review.grade}" />
                            </td>
                            <td>
                                <c:out value="${review.review}" />
                            </td>
                        </tr>
                    </tbody>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
    <p></p>
    <div class="getBack">
    <a href="/controller?command=SHOW_ADMIN_PAGE_COMMAND">${getBack}</a>
    </div>
    <%@ include file="footer/footer.jsp" %>
    </div>
</body>

</html>
