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

<html>
   <head>
         <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <style>
            <%@include file="/WEB-INF/css/labels_buttons_tables.css"%>
            <%@include file="/WEB-INF/css/tables.css"%>
          </style>
        </head>
   <body>
   <h2>${deleteCoursePage}</h2>
   <h4>${youAreMentor}</h4>
   <c:choose>
           <c:when test="${user_course.size() eq 0}">
           <p>${noCourses}</p>
               </c:when>
               <c:otherwise>
       <table border="1">
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
<form action="/controller?command=DELETE_COURSE_COMMAND" method="post">
           <div class="form-group">
                  <label>${courseName}</label>
                  <select name="Course_name">
                  <c:forEach items="${requestScope.user_course}" var="course">
                    <option value="${course.name}">${course.name}</option>
                     </c:forEach>
                  </select>
                  </div>
               <p></p>
                 <button type="submit" name="btnDeleteCourse" <c:if test="${user_course.size() == 0}"><c:out value="disabled='disabled'"/></c:if> >${btnDeleteCourse}</button>
                <button type="submit" name="btnGetBack">${getBack}</button>
                </div>
                </form>
             <a href = "/controller?command=LOG_OUT_COMMAND">Log out</a>
                  <p></p>
               <%@ include file="footer/footer.jsp" %>
           </body>
       </html>