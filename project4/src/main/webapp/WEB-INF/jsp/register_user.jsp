<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
   <head>
        <title>Elective</title>
       <h2>Let`s finish registration:</h2>
        </head>
    <body>
    <form action="/controller?command=REGISTER_USER_COMMAND" method="post">
     <div class="form-group">
            <label>First Name</label>
            <input name="lblFirstName" type="text" placeholder="Enter your first name">
          </div>
          <p></p>
              <div class="form-group">
            <label>Last Name</label>
            <input name="lblLastName" type="text" placeholder="Enter your last name" >
          </div>
          <p></p>
           <label>Group</label>
          <input name="lblGroup" type="text" placeholder="Enter your group number" >
        </div>
        <p></p>
            <input type="submit" name="btnRegister" value="Register" >
                <c:if test="${lblFirstName.size() == 0}">
                    <c:out value="disabled='disabled'"/>
                </c:if>
            </input>
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