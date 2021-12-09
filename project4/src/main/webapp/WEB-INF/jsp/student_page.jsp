<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/tld/hello_tag.tld" prefix="custom"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="hello" var="hello"/>
<fmt:message bundle="${loc}" key="yourCourses" var="yourCourses"/>
<fmt:message bundle="${loc}" key="yourCourseListIsEmpty" var="yourCourseListIsEmpty"/>
<fmt:message bundle="${loc}" key="courseId" var="courseId"/>
<fmt:message bundle="${loc}" key="courseName" var="courseName"/>
<fmt:message bundle="${loc}" key="courseStartDate" var="courseStartDate"/>
<fmt:message bundle="${loc}" key="CourseEndDate" var="CourseEndDate"/>
<fmt:message bundle="${loc}" key="btnSeeResults" var="btnSeeResults"/>
<fmt:message bundle="${loc}" key="btnGetCourse" var="btnGetCourse"/>
<fmt:message bundle="${loc}" key="btnDeleteCourse" var="btnDeleteCourse"/>

<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <%@ include file="header/header.jsp" %>
    <style>
        <%@include file="/WEB-INF/css/labels_buttons.css"%><%@include file="/WEB-INF/css/tables.css"%>
    </style>
</head>

<body>
    <div class="custom">
        <custom:hello userName="${hello}${current_user.first_name} ${current_user.last_name}" />
    </div>
    <h3>${yourCourses}</h3>
    <c:choose>
        <c:when test="${user_course.size() eq 0}">
            <div class="exception">
                <p>${yourCourseListIsEmpty}</p>
                <div>
        </c:when>
        <c:otherwise>
            <table border="1" table style="width:33%" style="text-align:center">
                <thead>
                    <tr>
                        <th>${courseId}</th>
                        <th>${courseName}</th>
                        <th>${courseStartDate}</th>
                        <th>${CourseEndDate}</th>
                    </tr>
                </thead>
                <c:forEach items="${ requestScope.user_course}" var="course">
                    <tbody>
                        <tr>
                            <td>
                                <c:out value="${course.id}" />
                            </td>
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
    <form action="/controller?command=USER_PAGE_COMMAND" method="post">
        <button type="submit" name="btnSeeResults">${btnSeeResults}</button>
        <button type="submit" name="btnGetCourse">${btnGetCourse}</button>
        <button type="submit" name="btnDeleteCourse">${btnDeleteCourse}</button>
    </form>
    <p></p>
    <%@ include file="footer/footer.jsp" %>
</body>

</html>