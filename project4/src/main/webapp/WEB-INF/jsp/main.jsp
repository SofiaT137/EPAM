<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="language" value="${not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resources.locale" var="loc"/>

<html>
   <head>
        <title>Elective</title>
       <h1>University elective web-site</h1>
        </head>
    <body>
    <form>
      <label>
        <p class="label-txt">ENTER YOUR LOGIN</p>
        <input type="text" class="input" placeholder="login" name="login" required>
        <div class="line-box">
          <div class="line"></div>
        </div>
      </label>
      <label>
        <p class="label-txt">ENTER YOUR PASSWORD</p>
        <input type="password" class="input" placeholder="password"name="password" required>
        <div class="line-box">
          <div class="line"></div>
        </div>
      </label>
      <p></p>
      <button type="submit" class="registerbtn">Register</button>
      <button type="submit" class="loginbtn">Log in</button>
    </form>
    <a id="reviews-page-link" href ="/controller?command=GET_ALL_REVIEW">Reviews page</a>
    <a id="users-page-link" href ="/controller?command=GET_ALL_USER">Users page</a>
    <a id="courses-page-link" href ="/controller?command=GET_ALL_COURSE">Courses page</a>
    <footer class="bg-light text-center text-lg-start">
        <div class="text-center p-3" style="background-color: rgba(88, 69, 16, 0.2);">
         © 2021 Copyright: Made by Sofia Tkachenia
       </div>
     </footer>
    </body>
</html>