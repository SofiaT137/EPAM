<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="letsUpdateACourse" var="letsUpdateACourse"/>
<fmt:message bundle="${loc}" key="yourCurrentCourses" var="yourCurrentCourses"/>
<fmt:message bundle="${loc}" key="youHaveNoCourses" var="youHaveNoCourses"/>
<fmt:message bundle="${loc}" key="courseId" var="courseId"/>
<fmt:message bundle="${loc}" key="courseName" var="courseName"/>
<fmt:message bundle="${loc}" key="courseStartDate" var="courseStartDate"/>
<fmt:message bundle="${loc}" key="CourseEndDate" var="CourseEndDate"/>
<fmt:message bundle="${loc}" key="invalidCourseName" var="invalidCourseName"/>
<fmt:message bundle="${loc}" key="newCourseData" var="newCourseData"/>
<fmt:message bundle="${loc}" key="StartDateLabel" var="StartDateLabel"/>
<fmt:message bundle="${loc}" key="EndDateLabel" var="EndDateLabel"/>
<fmt:message bundle="${loc}" key="btnUpdate" var="btnUpdate"/>
<fmt:message bundle="${loc}" key="getBack" var="getBack"/>
<fmt:message bundle="${loc}" key="next" var="next"/>
<fmt:message bundle="${loc}" key="previous" var="previous"/>
<fmt:message bundle="${loc}" key="cannotFindCourseByItsName" var="cannotFindCourseByItsName"/>
<fmt:message bundle="${loc}" key="youNotTheMentorOfCourse" var="youNotTheMentorOfCourse"/>
<fmt:message bundle="${loc}" key="error" var="error"/>
<fmt:message bundle="${loc}" key="exception" var="exception"/>


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
    <h2>${letsUpdateACourse}</h2>
    <h4>${yourCurrentCourses}</h4>
    <c:choose>
        <c:when test="${user_course.size() eq 0}">
            <h4>${youHaveNoCourses}</h4>
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
                      href="/controller?command=SHOW_UPDATE_COURSE_COMMAND&page=${current_page - 1}"
                      >${previous}</a
                    >
                  </td>
                </c:if>

                <c:forEach begin="1" end="${number_of_pages}" var="i">
                  <c:choose>
                    <c:when test="${current_page eq i}">
                      <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                      <td>
                        <a href="/controller?command=SHOW_UPDATE_COURSE_COMMAND&page=${i}"
                          >${i}</a
                        >
                      </td>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>

                <%--For displaying Next link --%>
                <c:if test="${current_page lt number_of_pages}">
                  <td>
                    <a
                      href="/controller?command=SHOW_UPDATE_COURSE_COMMAND&page=${current_page + 1}"
                      >${next}</a
                    >
                  </td>
                </c:if>
              </div>
            </c:otherwise>
          </c:choose>
          <p></p>
          <h4>${newCourseData}</h4>
    <form action="/controller?command=UPDATE_COURSE_COMMAND" method="post">
        <div class="form-group">
            <label>${courseName}</label>
            <input name="lblCourseName" type="text" placeholder="${CourseNameLabel}" title="${invalidCourseName}" required pattern="^[a-zA-Zа-яА-я '+.-]{2,30}$" />
        </div>
        <p></p>
        <div class="form-group">
            <label>${courseStartDate}</label>
            <input name="lblStartDate" type="date" placeholder="${StartDateLabel}" required pattern="^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$" />
        </div>
        <p></p>
        <div class="form-group">
            <label>${CourseEndDate}</label>
            <input name="lblEndDate" type="date" placeholder="${EndDateLabel}" required pattern="^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$" />
        </div>
        <p></p>
        <div class="invalid">
        <c:choose>
            <c:when test="${errorMsg eq 'cannotFindCourseByName'}">
                <p>${error}: ${cannotFindCourseByItsName}</p>
            </c:when>
            <c:when test="${errorMsg eq 'youNotTheMentor'}">
                <p>${error}: ${youNotTheMentorOfCourse}</p>
            </c:when>
            <c:otherwise>
                <c:if test="${errorMsg ne null}">
                    <p>${error}: ${errorMsg}</p>
                </c:if>
            </c:otherwise>
        </c:choose>
      </div>
        <button type="submit" name="btnUpdate">${btnUpdate}</button>
        <button type="submit" name="btnGetBack" onClick='location.href="/controller?command=SHOW_TEACHER_PAGE_COMMAND"'>${getBack}</button>
    </form>
    <p></p>
     <%@ include file="footer/footer.jsp" %>
    </body>
</html>