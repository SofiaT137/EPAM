<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
   <head>
       <h2>All the university courses reviews</h2>
        </head>
         <body>
      <c:choose>
              <c:when test="${all_reviews.size() eq 0}">
              <p>You have no courses reviews at the university!</p>
                  </c:when>
                  <c:otherwise>
          <table border="1" table style="width:250px" style="text-align:center">
               <thead>
                <tr>
                  <th>Review id</th>
                  <th>User id</th>
                  <th>Course id</th>
                  <th>Grade</th>
                  <th>Review </th>
                  </tr>
              </thead>
            <c:forEach items="${requestScope.all_reviews}" var="review">
           <tbody>
             <tr>
           <td><c:out value="${review.id}" /></td>
           <td><c:out value="${review.user_id}" /></td>
          <td><c:out value="${review.course_id}" /></td>
          <td><c:out value="${review.grade}" /></td>
          <td><c:out value="${review.review}" /></td>
           </tr>
           </tbody>
               </c:forEach>
              </table>
                 </c:otherwise>
                 </c:choose>
                 <p></p>
                   <a href = "/controller?command=SHOW_ADMIN_PAGE_COMMAND">Get Back</a>
             <%@ include file="footer/footer.jsp" %>
              </body>
            </html>