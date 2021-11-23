<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
   <head>
       <h2>All the university users:</h2>
        </head>
         <body>
      <c:choose>
              <c:when test="${all_users.size() eq 0}">
              <p>You have no users at the university! It is strange.</p>
                  </c:when>
                  <c:otherwise>
          <table border="1" table style="width:450px" style="text-align:center">
               <thead>
                <tr>
                  <th>User id</th>
                  <th>Account id</th>
                  <th>Group id</th>
                  <th>First name</th>
                  <th>Last name</th>
                 </tr>
              </thead>
            <c:forEach items="${requestScope.all_users}" var="user">
           <tbody>
             <tr>
          <td><c:out value="${user.id}" /></td>
          <td><c:out value="${user.account_id}" /></td>
          <td><c:out value="${user.group_id}" /></td>
          <td><c:out value="${user.first_name}" /></td>
          <td><c:out value="${user.last_name}" /></td>
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