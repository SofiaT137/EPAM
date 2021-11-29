<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
   <head>
        <h2>Course registration page</h2>
   </head>
   <body>
    <c:choose>
              <c:when test="${possible_courses.size() eq 0}">
              <p>You have not any possible to registration courses!</p>
              </c:when>
           <c:otherwise>
   <h3>Possible to registration courses:</h3>
    <table border="1">
            <thead>
              <tr>
                <th>Course number</th>
                <th>Course name</th>
                <th>Course start date</th>
                <th>Course end date</th>
              </tr>
            </thead>
        <c:forEach items="${requestScope.possible_courses}" var="course">
        <tbody>
          <tr>
              <td><c:out value="${course.id}" /></td>
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
   <form action="/controller?command=SIGN_UP_TO_COURSE_COMMAND" method="post">
           <div class="form-group">
              <div class="form-group">
                      <label>Course name:</label>
                      <select name="Course_name">
                      <c:forEach items="${requestScope.possible_courses}" var="course">
                        <option value="${course.name}">${course.name}</option>
                         </c:forEach>
                      </select>
                      </div>
                   <p></p>
                   <input type="submit" name="btnGetCourse" value="Get course" <c:if test="${possible_courses.size() == 0}"><c:out value="disabled='disabled'"/></c:if> />
                   <input type="submit" name="btnGetBack" value="Get Back" />
                   </div>
                   </form>
                   <a href = "/controller?command=LOG_OUT_COMMAND">Log out</a>
                    <p></p>
    <footer class="bg-light text-center text-lg-start">
                    <div class="text-center p-3" style="background-color: rgba(88, 69, 16, 0.2);">
                     © 2021 Copyright: Made by Sofia Tkachenia
                   </div>
                 </footer>
                </body>
            </html>