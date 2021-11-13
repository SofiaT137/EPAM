<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
   <head>
        <title>Elective</title>
       <h1>University elective web-site</h1>
        </head>
    <body>
   		<form action="/controller?command=SELECT_REGISTRATION_OR_LOG_IN" method="post">
   		     <div class="form-group">
            <label>Login</label>
            <input name="lblLogin" type="text" placeholder="Enter your login">
          </div>
          <p></p>
              <div class="form-group">
            <label>Password</label>
            <input name="lblPassword" type="password" placeholder="Enter your password" >
          </div>
          <p></p>
          Select who you are: <SELECT NAME="Role:">
          <OPTION VALUE="Student">Student
          <OPTION VALUE="Teacher">Teacher
          </SELECT>
          <br></br>
            <input type="submit" name="btnRegister" value="Register" />
            <input type="submit" name="btnLogIn" value="LogIn" />
        </form>
    <footer class="bg-light text-center text-lg-start">
        <div class="text-center p-3" style="background-color: rgba(88, 69, 16, 0.2);">
         © 2021 Copyright: Made by Sofia Tkachenia
       </div>
     </footer>
    </body>
</html>