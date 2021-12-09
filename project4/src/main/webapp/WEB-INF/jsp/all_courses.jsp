<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="allTheUniversityCourses" var="allTheUniversityCourses"/>
<fmt:message bundle="${loc}" key="noCourses" var="noCourses"/>
<fmt:message bundle="${loc}" key="courseName" var="courseName"/>
<fmt:message bundle="${loc}" key="courseStartDate" var="courseStartDate"/>
<fmt:message bundle="${loc}" key="CourseEndDate" var="CourseEndDate"/>
<fmt:message bundle="${loc}" key="getBack" var="getBack"/>

<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
    <%@ include file="header/header.jsp" %>
    <style>
        <%@include file="/WEB-INF/css/tables.css"%>
        <%@include file="/WEB-INF/css/labels_buttons.css"%>
    </style>
</head>

<body>
    <div class="wrapper">
    <h2>${allTheUniversityCourses}</h2>
    <c:choose>
        <c:when test="${all_courses.size() eq 0}">
            <p>${noCourses}</p>
        </c:when>
        <c:otherwise>
            <table border="1" table style="width: 33%" style="text-align: center">
                <thead>
                    <tr>
                        <th>${courseName}</th>
                        <th>${courseStartDate}</th>
                        <th>${CourseEndDate}</th>
                    </tr>
                </thead>
                <c:forEach items="${requestScope.all_courses}" var="course">
                    <tbody>
                        <tr>
                            <td>
                                <c:out value="${course.name}" />
                            </td>
                            <td>
                                <c:out value="${course.startCourse}" />
                            </td>
                            <td>
                                <c:out value="${course.endCourse}" />
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