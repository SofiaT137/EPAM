<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="ru"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="website" var="website"/>
<fmt:message bundle="${loc}" key="elective" var="elective"/>
<fmt:message bundle="${loc}" key="login" var="login"/>
<fmt:message bundle="${loc}" key="password" var="password"/>
<fmt:message bundle="${loc}" key="enterYourLogin" var="enterYourLogin"/>
<fmt:message bundle="${loc}" key="enterYourPassword" var="enterYourPassword"/>
<fmt:message bundle="${loc}" key="btnRegister" var="btnRegister"/>
<fmt:message bundle="${loc}" key="btnLogIn" var="btnLogIn"/>

<html>
      <head>
        <title>${elective}</title>
       <h1>${website}</h1>
        </head>
    <body BACKGROUND= "/img/studying.jpg" style='background-size:100%'>
     	<form action="/controller?command=SELECT_REGISTRATION_OR_LOG_IN" method="post">
   		     <div class="form-group">
            <label>${login}</label>
            <input name="lblLogin" type="text" placeholder="${enterYourLogin}">
          </div>
          <p></p>
              <div class="form-group">
            <label>${password}</label>
            <input name="lblPassword" type="password" placeholder="${enterYourPassword}">
          </div>
          <p></p>
            <input type="submit" name="btnRegister" value="${btnRegister}" />
            <input type="submit" name="btnLogIn" value="${btnLogIn}" />
        </form>
    <footer class="bg-light text-center text-lg-start">
        <div class="text-center p-3" style="background-color: rgba(88, 69, 16, 0.2);">
         © 2021 Copyright: Made by Sofia Tkachenia
       </div>
     </footer>
    </body>
</html>