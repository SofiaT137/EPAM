<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/hello_tag.tld" prefix="custom"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
   <head>
        <custom:hello userName="${current_user.first_name} ${current_user.last_name}"/>
        </head>
    <body>
    <h2>You are the mentor of the courses:</h2>
    <c:choose>
        <c:when test="${user_course.size() eq 0}">
        <p>Your course list is empty! For select a course, press the button "Get course"</p>
            </c:when>
            <c:otherwise>
           <table border="1">
            <thead>
              <tr>
                <th>Course number</th>
                <th>Course name</th>
                <th>Course start date</th>
                <th>Course end date</th>
              </tr>
            </thead>
       <c:forEach items="${ requestScope.user_course}" var="course">
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
         <form action="/controller?command=TEACHER_PAGE_COMMAND" method="post">
                  <input type="submit" name="btnRateStudent" value="Rate Student" />
                  <input type="submit" name="btnCreateCourse" value="Create Course" />
                  <input type="submit" name="btnDeleteCourse" value="Delete course" />
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