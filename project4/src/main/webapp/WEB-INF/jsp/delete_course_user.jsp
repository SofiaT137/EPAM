<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
   <head>
        <h2>Delete Course Page</h2>
   </head>
   <h2>Your courses </h2>
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
         <p></p>
           <form action="/controller?command=DELETE_USER_COURSE_COMMAND" method="post">
                   <div class="form-group">
                               <label>Delete</label>
                               <input name="lblDelete" type="text" placeholder="Enter the course number">
                             </div>
                             <p></p>
                           <input type="submit" name="btnDeleteCourse" value="Delete course" />
                           <input type="submit" name="btnGetBack" value="Get Back" />
                           <input type="submit" name="btnLogOut" value="Log out" />
                       </form>
             <footer class="bg-light text-center text-lg-start">
                 <div class="text-center p-3" style="background-color: rgba(88, 69, 16, 0.2);">
                  © 2021 Copyright: Made by Sofia Tkachenia
                </div>
              </footer>
             </body>
         </html>