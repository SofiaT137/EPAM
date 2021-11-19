<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
   <head>
          <title>Elective</title>
       <h2>Let`s finish registration:</h2>
        </head>
    <body>
        <form action="/controller?command=REGISTER_USER_COMMAND" method="post">
     <div class="form-group">
            <label>First Name</label>
            <input name="lblFirstName" type="text" placeholder="${FirstNameLabel}" required pattern= "^[a-zA-Z '.-]{2,20}*$"/>
          </div>
          <p></p>
              <div class="form-group">
            <label>Last Name</label>
            <input name="lblLastName" type="text" placeholder="${LastNameLabel}" required pattern= "^[a-zA-Z '.-]{2,20}*$" />
             </div>
          <p></p>
           <label>Group</label>
          <input name="lblGroup" type="text" placeholder="${GroupLabel}" required pattern="^[0-9]{2,7}*$" />
          </div>
        <p></p>
            <input type="submit" name="btnRegister" value="Register"/>
            </form>
            <p></p>
            <a href = "/controller?command=LOG_OUT_COMMAND">Log out</a>
             </body>
    <footer class="bg-light text-center text-lg-start">
        <div class="text-center p-3" style="background-color: rgba(88, 69, 16, 0.2);">
         © 2021 Copyright: Made by Sofia Tkachenia
       </div>
     </footer>
</html>