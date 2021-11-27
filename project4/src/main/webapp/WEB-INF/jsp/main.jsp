<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="website" var="website"/>
<fmt:message bundle="${loc}" key="elective" var="elective"/>
<fmt:message bundle="${loc}" key="ru" var="ru"/>
<fmt:message bundle="${loc}" key="en" var="en"/>
<fmt:message bundle="${loc}" key="selectLanguage" var="selectLanguage"/>
<fmt:message bundle="${loc}" key="login" var="login"/>
<fmt:message bundle="${loc}" key="password" var="password"/>
<fmt:message bundle="${loc}" key="enterYourLogin" var="enterYourLogin"/>
<fmt:message bundle="${loc}" key="enterYourPassword" var="enterYourPassword"/>
<fmt:message bundle="${loc}" key="btnRegister" var="btnRegister"/>
<fmt:message bundle="${loc}" key="btnLogIn" var="btnLogIn"/>

<html>
      <head>
      <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
      <style><%@include file="/WEB-INF/css/main.css"%></style>
       <title>${elective}</title>
         <h1>${website}</h1>
         </head>
         <body>
            <form action="/controller?command=SELECT_REGISTRATION_OR_LOG_IN" method="post">
               <div class="btn-group" role="group" aria-label="Basic example">
                 <button type="submit" class="btn btn-primary" name="btnRussian">Russian</button>
                 <button type="submit" class="btn btn-primary" name="btnEnglish">English</button>
                </div>
               <label>
                <p class="label-txt">${login}</p>
                <input name="lblLogin" type="text" class = "input" placeholder="${enterYourLogin}">
                <div class="line-box">
                  <div class="line"></div>
                </div>
              </label>
              <label>
                <p class="label-txt">${password}</p>
                <input name="lblPassword" type="password" class = "input" placeholder="${enterYourPassword}">
                <div class="line-box">
                  <div class="line"></div>
                </div>
              </label>
              <button type="submit" name="btnRegister">${btnRegister}</button>
              <button type="submit" name="btnLogIn">${btnLogIn}</button>
             </form>
          </body>
          <div class="footer">
          <%@ include file="footer/footer.jsp" %>
          </div>
   </html>