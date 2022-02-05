<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="deleteCoursePage" var="deleteCoursePage"/>
<fmt:message bundle="${loc}" key="youAreMentor" var="youAreMentor"/>
<fmt:message bundle="${loc}" key="noCourses" var="noCourses"/>
<fmt:message bundle="${loc}" key="courseName" var="courseName"/>
<fmt:message bundle="${loc}" key="courseStartDate" var="courseStartDate"/>
<fmt:message bundle="${loc}" key="CourseEndDate" var="CourseEndDate"/>
<fmt:message bundle="${loc}" key="btnDeleteCourse" var="btnDeleteCourse"/>
<fmt:message bundle="${loc}" key="getBack" var="getBack"/>
<fmt:message bundle="${loc}" key="next" var="next"/>
<fmt:message bundle="${loc}" key="previous" var="previous"/>
<fmt:message bundle="${loc}" key="cannotFindThisCourseByName" var="cannotFindThisCourseByName"/>
<fmt:message bundle="${loc}" key="cannotDeleteAllUserFromThisCourse" var="cannotDeleteAllUserFromThisCourse"/>
<fmt:message bundle="${loc}" key="youNotTheMentorOfCourse" var="youNotTheMentorOfCourse"/>
<fmt:message bundle="${loc}" key="cannotDeleteThisFinishedCourse" var="cannotDeleteThisFinishedCourse"/>
<fmt:message bundle="${loc}" key="error" var="error"/>

<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <%@ include file="header/header.jsp" %>
    <style>
        <%@include file="/WEB-INF/css/labels_buttons.css"%><%@include file="/WEB-INF/css/tables.css"%>
    </style>
</head>

<body>
    <h2>${deleteCoursePage}</h2>
    <h4>${youAreMentor}</h4>
    <c:choose>
        <c:when test="${user_course.size() eq 0}">
            <div class="exception">
                <p>${noCourses}</p>
            </div>
        </c:when>
        <c:otherwise>
            <table border="1" table style="width:33%" style="text-align:center">
                <thead>
                    <tr>
                        <th>${courseName}</th>
                        <th>${courseStartDate}</th>
                        <th>${CourseEndDate}</th>
                    </tr>
                </thead>
                <c:forEach items="${requestScope.user_course}" var="course">
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
            <div class="paggination">
                    <c:if test="${current_page != 1}">
                        <td>
                            <a
                                href="/controller?command=SHOW_DELETE_COURSE_PAGE_COMMAND&page=${current_page - 1}">${previous}</a>
                        </td>
                    </c:if>

                    <c:forEach begin="1" end="${number_of_pages}" var="i">
                        <c:choose>
                            <c:when test="${current_page eq i}">
                                <td>${i}</td>
                            </c:when>
                            <c:otherwise>
                                <td>
                                    <a href="/controller?command=SHOW_DELETE_COURSE_PAGE_COMMAND&page=${i}">${i}</a>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <%--For displaying Next link --%>
                        <c:if test="${current_page lt number_of_pages}">
                            <td>
                                <a
                                    href="/controller?command=SHOW_DELETE_COURSE_PAGE_COMMAND&page=${current_page + 1}">${next}</a>
                            </td>
                        </c:if>
                </div>
            </c:otherwise>
        </c:choose>
        <p></p>
    <form action="/controller?command=DELETE_COURSE_COMMAND" method="post">
        <div class="form-group">
            <label>${courseName}</label>
            <input name = "courseName" list = "courses" placeholder = "Select course name" autocomplete="on" />
            <datalist id = "courses">
                <c:forEach items="${requestScope.user_course}" var="course">
                    <option value="${course.name}">${course.name}</option>
                </c:forEach>
            </datalist>
        </div>
        <p></p>
    <div class="invalid">
        <c:choose>
        <c:when test="${errorMsg eq '${cannotFindThisCourse'}}">
            <p>${error}: ${cannotFindThisCourseByName}</p>
        </c:when>
        <c:when test="${errorMsg eq 'cannotDeleteAllUserFromCourse'}">
            <p>${error}: ${cannotDeleteAllUserFromThisCourse}</p>
        </c:when>
        <c:when test="${errorMsg eq '${youNotTheMentor'}}">
            <p>${error}: ${youNotTheMentorOfCourse}</p>
        </c:when>
        <c:when test="${errorMsg eq 'cannotDeleteFinishedCourse'}">
            <p>${error}: ${cannotDeleteThisFinishedCourse}</p>
        </c:when>
            <c:otherwise>
                <c:if test="${errorMsg ne null}">
                    <p>${error}:${errorMsg}</p>
                </c:if>
            </c:otherwise>
        </c:choose>
    </div>
      <button type="submit" name="btnDeleteCourse" <c:if test="${notFinished.size() == 0}"> <c:out value="disabled='disabled'" /> </c:if>>${btnDeleteCourse}</button>
      <button type="submit" name="btnGetBack">${getBack}</button>
      </form>
      <p></p>
       <%@ include file="footer/footer.jsp" %>
      </body>
  </html>