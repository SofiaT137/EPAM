<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
   <head>
       <h2>All university courses: </h2>
        </head>
         <body>
              <c:choose>
              <c:when test="${all_courses.size() eq 0}">
              <p>No courses at this university!</p>
                  </c:when>
                  <c:otherwise>
          <table border="1">
               <thead>
                 <tr>
                   <th>Course name</th>
                   <th>Course start date</th>
                   <th>Course end date</th>
                   </tr>
               </thead>
                <c:forEach items="${requestScope.all_courses}" var="course">
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
              <a href = "/controller?command=SHOW_ADMIN_PAGE_COMMAND">Get Back</a>
              </body>
              <p></p>
     <footer class="bg-light text-center text-lg-start">
         <div class="text-center p-3" style="background-color: rgba(88, 69, 16, 0.2);">
          © 2021 Copyright: Made by Sofia Tkachenia
        </div>
      </footer>
     </html>