<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="selectCourseForReview" var="selectCourseForReview"/>
<fmt:message bundle="${loc}" key="youAreNotMentor" var="youAreNotMentor"/>
<fmt:message bundle="${loc}" key="courseName" var="courseName"/>
<fmt:message bundle="${loc}" key="courseStartDate" var="courseStartDate"/>
<fmt:message bundle="${loc}" key="CourseEndDate" var="CourseEndDate"/>
<fmt:message bundle="${loc}" key="btnFillReview" var="btnFillReview"/>
<fmt:message bundle="${loc}" key="getBack" var="getBack"/>

<html>
   <head>
        <h2>${selectCourseForReview}</h2>
   </head>
   <body>
    <c:choose>
              <c:when test="${finished_course.size() eq 0}">
              <p>${youAreNotMentor}</p>
              </c:when>
           <c:otherwise>
   <h3>${possibleToCheckCourse}</h3>
    <table border="1">
            <thead>
              <tr>
                <th>${courseName}</th>
                <th>${courseStartDate}</th>
                <th>${CourseEndDate}</th>
              </tr>
            </thead>
        <c:forEach items="${requestScope.finished_course}" var="course">
        <tbody>
          <tr>
              <td><c:out value="${course.name}" /></td>
              <td><c:out value="${course.startCourse}" /></td>
              <td><c:out value="${course.endCourse}" /></td>
            </tr>
        </tbody>
      </c:forEach>
  </table>
         </c:otherwise>
         </c:choose>
  <p></p>
   <form action="/controller?command=TEACHER_SELECT_COURSE_COMMAND" method="post">
                 <div class="form-group">
                      <label>${courseName}</label>
                       <select name="Course_name">
                       <c:forEach items="${requestScope.finished_course}" var="course">
                         <option value="${course.name}">${course.name}</option>
                          </c:forEach>
                       </select>
                       </div>
                    <p></p>
                <button type="submit" name="btnFillReview"  <c:if test="${user_course.size() == 0}"><c:out value="disabled='disabled'"/></c:if>>${btnFillReview}</button>
                <button type="submit" name="btnGetBack">${getBack}</button>
                   </div>
                   </form>
                   <a href = "/controller?command=LOG_OUT_COMMAND">Log out</a>
                <%@ include file="footer/footer.jsp" %>
            </body>
        </html>