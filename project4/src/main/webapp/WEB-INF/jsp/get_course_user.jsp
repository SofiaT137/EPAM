<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="courseRegistrationPage" var="courseRegistrationPage"/>
<fmt:message bundle="${loc}" key="noAnyPossibleToRegistrationCourses" var="noAnyPossibleToRegistrationCourses"/>
<fmt:message bundle="${loc}" key="possibleToRegistrationCourses" var="possibleToRegistrationCourses"/>
<fmt:message bundle="${loc}" key="courseId" var="courseId"/>
<fmt:message bundle="${loc}" key="courseName" var="courseName"/>
<fmt:message bundle="${loc}" key="courseStartDate" var="courseStartDate"/>
<fmt:message bundle="${loc}" key="CourseEndDate" var="CourseEndDate"/>
<fmt:message bundle="${loc}" key="btnGetCourse" var="btnGetCourse"/>
<fmt:message bundle="${loc}" key="getBack" var="getBack"/>


<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <%@ include file="header/header.jsp" %>
    <style>
        <%@include file="/WEB-INF/css/labels_buttons.css"%><%@include file="/WEB-INF/css/tables.css"%>
    </style>
</head>

<body>
    <h2>${courseRegistrationPage}</h2>
    <c:choose>
        <c:when test="${possible_courses.size() eq 0}">
            <div class="exception">
                <p>${noAnyPossibleToRegistrationCourses}</p>
                <div>
        </c:when>
        <c:otherwise>
            <h3>${possibleToRegistrationCourses}</h3>
            <table border="1" table style="width:33%" style="text-align:center">
                <thead>
                    <tr>
                        <th>${courseId}</th>
                        <th>${courseName}</th>
                        <th>${courseStartDate}</th>
                        <th>${CourseEndDate}</th>
                    </tr>
                </thead>
                <c:forEach items="${requestScope.possible_courses}" var="course">
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
    <form action="/controller?command=SIGN_UP_TO_COURSE_COMMAND" method="post">
        <div class="form-group">
            <label>${courseName}</label>
            <select name="Course_name">
                <c:forEach items="${requestScope.possible_courses}" var="course">
                    <option value="${course.name}">${course.name}</option>
                </c:forEach>
            </select>
        </div>
        <p></p>
        <button type="submit" name="btnGetCourse" <c:if test="${possible_courses.size() == 0}">
            <c:out value="disabled='disabled'" />
            </c:if> >${btnGetCourse}
        </button>
        <button type="submit" name="btnGetBack">${getBack}</button>
        </div>
    </form>
    <p></p>
    <%@ include file="footer/footer.jsp" %>
</body>

</html>