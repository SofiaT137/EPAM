<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
   <head>
       <h2>Let`s update a course </h2>
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
                   <th>Course id</th>
                   <th>Course name</th>
                   <th>Course start date</th>
                   <th>Course end date</th>
                   </tr>
               </thead>
                <c:forEach items="${requestScope.user_course}" var="course">
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
         <form action="/controller?command=UPDATE_COURSE_COMMAND" method="post">
         <div class="form-group">
             <h3>New course data:</h3>
              <label>Course name:</label>
               <select name="Course_name">
               <c:forEach items="${requestScope.user_course}" var="course">
                 <option value="${course.name}">${course.name}</option>
                  </c:forEach>
               </select>
               </div>
            <p></p>
               <div class="form-group">
             <label>Course start date: </label>
             <input name="lblStartDate" type="date" placeholder="${StartDateLabel}" required pattern = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$" />
              </div>
           <p></p>
           <div class="form-group">
            <label>Course end date: </label>
           <input name="lblEndDate" type="date" placeholder="${EndDateLabel}" required pattern = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$" />
           </div>
            <p></p>
             <input type="submit" name="btnUpdate" value="Update course" />
             <input type="submit" name="btnGetBack" value="Get Back" onClick='location.href="/controller?command=SHOW_TEACHER_PAGE_COMMAND"' />
             </form>
             <p></p>
              <a href = "/controller?command=LOG_OUT_COMMAND">Log out</a>
              </body>
        <div class="footer"><%@ include file="footer/footer.jsp" %></div>
     </html>