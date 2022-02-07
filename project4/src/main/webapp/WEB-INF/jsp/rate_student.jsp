<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="Elective" var="Elective"/>
<fmt:message bundle="${loc}" key="courseStudents" var="courseStudents"/>
<fmt:message bundle="${loc}" key="noCourseStudent" var="noCourseStudent"/>
<fmt:message bundle="${loc}" key="groupNumber" var="groupNumber"/>
<fmt:message bundle="${loc}" key="firstName" var="firstName"/>
<fmt:message bundle="${loc}" key="lastName" var="lastName"/>
<fmt:message bundle="${loc}" key="getBack" var="getBack"/>
<fmt:message bundle="${loc}" key="firstNameLabel" var="firstNameLabel"/>
<fmt:message bundle="${loc}" key="lastNameLabel" var="lastNameLabel"/>
<fmt:message bundle="${loc}" key="Group" var="Group"/>
<fmt:message bundle="${loc}" key="GradeLabel" var="GradeLabel"/>
<fmt:message bundle="${loc}" key="ReviewLabel" var="ReviewLabel"/>
<fmt:message bundle="${loc}" key="Grade" var="Grade"/>
<fmt:message bundle="${loc}" key="Review" var="Review"/>
<fmt:message bundle="${loc}" key="btnAddReview" var="btnAddReview"/>
<fmt:message bundle="${loc}" key="invalidFirstName" var="invalidFirstName"/>
<fmt:message bundle="${loc}" key="invalidLastName" var="invalidLastName"/>
<fmt:message bundle="${loc}" key="next" var="next"/>
<fmt:message bundle="${loc}" key="previous" var="previous"/>
<fmt:message bundle="${loc}" key="cannotFindUserByFullName" var="cannotFindUserByFullName"/>
<fmt:message bundle="${loc}" key="cannotFindStudentInGroup" var="cannotFindStudentInGroup"/>
<fmt:message bundle="${loc}" key="thisStudentHasReview" var="thisStudentHasReview"/>
<fmt:message bundle="${loc}" key="incorrectGrade" var="incorrectGrade"/>
<fmt:message bundle="${loc}" key="error" var="error"/>

<!DOCTYPE HTML>

<html>

<head>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <%@ include file="header/header.jsp" %>
    <style>
      <%@include file="/WEB-INF/css/labels_buttons.css"%><%@include file="/WEB-INF/css/tables.css"%>
    </style>
</head>

<body>
  <div class="wrapper">
    <h2>${courseStudents}</h2>
    <c:choose>
      <c:when test="${students_course.size() eq 0}">
        <div class="exception">
          <p>${noCourseStudent}</p>
        </div>
      </c:when>
      <c:otherwise>
        <table border="1" table style="width:33%" style="text-align:center">
          <thead>
            <tr>
              <th>${groupNumber}</th>
              <th>${firstName}</th>
              <th>${lastName}</th>
            </tr>
          </thead>
          <c:forEach items="${requestScope.students_course}" var="student">
            <tbody>
              <tr>
                <td>
                  <c:out value="${student.groupName}" />
                </td>
                <td>
                  <c:out value="${student.firstName}" />
                </td>
                <td>
                  <c:out value="${student.lastName}" />
                </td>
              </tr>
            </tbody>
          </c:forEach>
        </table>
        <div class="paggination">
          <c:if test="${current_page != 1}">
            <td>
              <a href="/controller?command=SHOW_RATE_PAGE_COMMAND&page=${current_page - 1}">${previous}</a>
            </td>
          </c:if>

          <c:forEach begin="1" end="${number_of_pages}" var="i">
            <c:choose>
              <c:when test="${current_page eq i}">
                <td>${i}</td>
              </c:when>
              <c:otherwise>
                <td>
                  <a href="/controller?command=SHOW_RATE_PAGE_COMMAND&page=${i}">${i}</a>
                </td>
              </c:otherwise>
            </c:choose>
          </c:forEach>

          <%--For displaying Next link --%>
            <c:if test="${current_page lt number_of_pages}">
              <td>
                <a href="/controller?command=SHOW_RATE_PAGE_COMMAND&page=${current_page + 1}">${next}</a>
              </td>
            </c:if>
        </div>
      </c:otherwise>
    </c:choose>
    <p></p>
    <form action="/controller?command=RATE_STUDENT_COMMAND" method="post">
      <div class="form-group">
        <label>${firstName}</label>
        <input name="lblFirstName" type="text" title="${invalidFirstName}" placeholder="${firstNameLabel}" required pattern="^[a-zA-Zа-яА-Я '.-]{2,20}$" />
      </div>
      <p></p>
      <div class="form-group">
        <label>${lastName}</label>
        <input name="lblLastName" type="text" title="${invalidLastName}" placeholder="${lastNameLabel}" required pattern="^[a-zA-Zа-яА-Я '.-]{2,20}$" />
      </div>
      <p></p>
      <div class="form-group">
        <label>${groupNumber}</label>
        <input name="groupName" list="groups" placeholder="Select group name" autocomplete="on" />
        <datalist id="groups">
          <c:forEach items="${requestScope.all_groups}" var="group">
            <option value="${group.name}">${group.name}</option>
          </c:forEach>
        </datalist>
      </div>
      <p></p>
      <div class="form-group">
        <label>${Grade}</label>
        <input name="lblGrade" type="number" placeholder="${GradeLabel}" required pattern="^[0-9]{1,2}$" />
      </div>
      <p></p>
      <div class="form-group">
        <label>${Review}</label>
        <input name="lblReview" type="text" placeholder="${ReviewLabel}" />
      </div>
      <p></p>
      <div class="invalid">
        <c:choose>
          <c:when test="${errorMsg eq 'cannotFindUserByFullName'}">
            <p>${error}:${cannotFindUserByFullName}</p>
          </c:when>
          <c:when test="${errorMsg eq 'cannotFindStudentInGroup'}">
            <p>${error}:${cannotFindStudentInGroup}</p>
          </c:when>
          <c:when test="${errorMsg eq 'incorrectGrade'}">
            <p>${error}: ${incorrectGrade}</p>
          </c:when>
          <c:otherwise>
            <c:if test="${errorMsg ne null}">
              <p>${error}: ${errorMsg}</p>
            </c:if>
          </c:otherwise>
        </c:choose>
      </div>
      <button type="submit" name="btnAddReview" <c:if test="${students_course.size() == 0}">
        <c:out value="disabled='disabled'" />
        </c:if>>${btnAddReview}
      </button>
      <button type="submit" name="btnGetBack" onClick='location.href="/controller?command=SHOW_TEACHER_COURSE_COMMAND"'>${getBack}</button>
    </form>
    <p></p>
    </div>
    <%@ include file="footer/footer.jsp" %>
</body>

</html>