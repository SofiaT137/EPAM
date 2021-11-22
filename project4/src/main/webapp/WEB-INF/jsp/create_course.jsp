<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
   <head>
       <h2>Let`s create a course: </h2>
        </head>
         <body>
           <h2>Your current courses: </h2>
    <c:choose>
              <c:when test="${user_course.size() eq 0}">
              <p>You have no current courses!</p>
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
    <form action="/controller?command=CREATE_COURSE_COMMAND" method="post">
      <div class="form-group">
             <label>Course name: name:</label>
             <input name="lblCourseName" type="text" placeholder="${CourseNameLabel}" />
           </div>
           <p></p>
               <div class="form-group">
             <label>Course start date: </label>
             <input name="lblStartDate" type="date" placeholder="${StartDateLabel}" />
              </div>
           <p></p>
           <div class="form-group">
            <label>Course end date: </label>
           <input name="lblEndDate" type="date" placeholder="${EndDateLabel}" />
           </div>
            <p></p>
             <input type="submit" name="btnAddCourse" value="Add course"/>
             <input type="submit" name="btnGetBack" value="Get Back" />
             </form>
              <a href = "/controller?command=LOG_OUT_COMMAND">Log out</a>
              </body>
              <p></p>
     <footer class="bg-light text-center text-lg-start">
         <div class="text-center p-3" style="background-color: rgba(88, 69, 16, 0.2);">
          © 2021 Copyright: Made by Sofia Tkachenia
        </div>
      </footer>
     </html>