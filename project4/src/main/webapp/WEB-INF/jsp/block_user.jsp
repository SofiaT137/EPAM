<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
   <head>
       <h2>Block user page:</h2>
        </head>
         <body>
           <h2>All the blocked users: </h2>
    <c:choose>
              <c:when test="${blocked_users.size() eq 0}">
              <p>No blocked users!</p>
                  </c:when>
                  <c:otherwise>
          <table border="1" table style="width:250px" style="text-align:center">
               <thead>
                 <tr>
                   <th>User id</th>
                   <th>First name</th>
                   <th>Last name</th>
                   </tr>
               </thead>
                <c:forEach items="${requestScope.blocked_users}" var="user">
               <tbody>
                 <tr>
               <td><c:out value="${user.id}" /></td>
              <td><c:out value="${user.first_name}" /></td>
              <td><c:out value="${user.last_name}" /></td>
               </tr>
               </tbody>
             </c:forEach>
         </table>
            </c:otherwise>
            </c:choose>
            <p></p>
        <form action="/controller?command=BLOCK_USER_COMMAND" method="post">
      <div class="form-group">
             <label>First name: </label>
             <input name="lblFirstName" type="text" placeholder="${FirstNameLabel}" required pattern="^[a-zA-Zа-яА-Я '.-]{2,20}$" />
           </div>
           <p></p>
               <div class="form-group">
             <label>Last name:</label>
             <input name="lblLastName"type="text" placeholder="${LastNameLabel}" required pattern="^[a-zA-Zа-яА-Я '.-]{2,20}$" />
              </div>
           <p></p>
             <label>Group:</label>
              <select name="Group">
              <c:forEach items="${requestScope.university_groups}" var="group">
                <option value="${group.name}">${group.name}</option>
                 </c:forEach>
              </select>
           <p></p>
             <input type="submit" name="btnUnBlockUser" value="Unblock User"/>
             <input type="submit" name="btnBlockUser" value="Block User"/>
             <input type="submit" name="btnGetBack" value="Get Back" onClick='location.href="/controller?command=SHOW_ADMIN_PAGE_COMMAND"' />
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