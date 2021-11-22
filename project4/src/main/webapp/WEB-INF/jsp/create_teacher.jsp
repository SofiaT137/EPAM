<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
   <head>
       <h2>Create a teacher</h2>
        </head>
         <body>
           <h2>All teachers at the university: </h2>
    <c:choose>
              <c:when test="${all_teachers.size() eq 0}">
              <p>You have no teacher at the university! It is strange.</p>
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
                <c:forEach items="${requestScope.all_teachers}" var="teacher">
               <tbody>
                 <tr>
               <td><c:out value="${teacher.id}" /></td>
              <td><c:out value="${teacher.first_name}" /></td>
              <td><c:out value="${teacher.last_name}" /></td>
               </tr>
               </tbody>
             </c:forEach>
         </table>
            </c:otherwise>
            </c:choose>
            <p></p>
    <form action="/controller?command=CREATE_TEACHER_COMMAND" method="post">
      <div class="form-group">
             <label>Login:</label>
             <input name="lblLogin" type="text" placeholder="${LoginLabel}" />
           </div>
           <p></p>
               <div class="form-group">
             <label>Password:</label>
             <input name="lblPassword" type="text" placeholder="${PasswordLabel}" />
              </div>
           <p></p>
           <div class="form-group">
            <label>First name:</label>
           <input name="lblFirstName" type="text" placeholder="${FirstNameLabel}" />
           </div>
            <p></p>
             <div class="form-group">
                <label>Last name:</label>
               <input name="lblLastName" type="text" placeholder="${LastNameLabel}" />
               </div>
                <p></p>
             <input type="submit" name="btnAddTeacher" value="Add Teacher"/>
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