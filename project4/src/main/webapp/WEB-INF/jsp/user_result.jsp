<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
   <head>
       <h2>Your results:</h2>
        </head>
    <body>
   <table border="1">
       <c:forEach items="${ requestScope.user_review}" var="review">
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
   <p></p>
         <form action="/controller?command=GET_BACK_OR_LOG_OUT_COMMAND" method="post">
                  <input type="submit" name="btnGetBack" value="Get Back" />
                  <input type="submit" name="btnLogOut" value="Log out" />
              </form>
      <p></p>
    <footer class="bg-light text-center text-lg-start">
        <div class="text-center p-3" style="background-color: rgba(88, 69, 16, 0.2);">
         © 2021 Copyright: Made by Sofia Tkachenia
       </div>
     </footer>
    </body>
</html>