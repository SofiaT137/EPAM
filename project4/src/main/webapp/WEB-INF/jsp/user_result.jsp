<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
   <head>
       <h2>Your results:</h2>
        </head>
       <body>
       <c:choose>
           <c:when test="${user_review.size() eq 0}">
           <p>You have not any reviews!</p>
           </c:when>
        <c:otherwise>
   <table border="1">
       <c:forEach items="${requestScope.user_review}" var="review">
         <thead>
           <tr>
             <th>Course name</th>
             <th>Grade</th>
             <th>Review</th>
           </tr>
         </thead>
         <tbody>
           <tr>
               <td><c:out value="${review.course_name}" /></td>
               <td><c:out value="${review.grade}" /></td>
               <td><c:out value="${review.review}" /></td>
             </tr>
         </tbody>
       </c:forEach>
   </table>
          </c:otherwise>
          </c:choose>
   <p></p>
         <form action="/controller?command=SHOW_USER_PAGE_COMMAND" method="post">
                  <input type="submit" name="btnGetBack" value="Get Back" />
                </form>
                <a href = "/controller?command=LOG_OUT_COMMAND">Log out</a>
          <p></p>
      <p></p>
    <footer class="bg-light text-center text-lg-start">
        <div class="text-center p-3" style="background-color: rgba(88, 69, 16, 0.2);">
         © 2021 Copyright: Made by Sofia Tkachenia
       </div>
     </footer>
    </body>
</html>