<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/WEB-INF/tld/hello_tag.tld" prefix="custom"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="hello" var="hello"/>
<fmt:message bundle="${loc}" key="showCourses" var="showCourses"/>
<fmt:message bundle="${loc}" key="showUsers" var="showUsers"/>
<fmt:message bundle="${loc}" key="showReviews" var="showReviews"/>
<fmt:message bundle="${loc}" key="createTeacher" var="createTeacher"/>
<fmt:message bundle="${loc}" key="createGroup" var="createGroup"/>
<fmt:message bundle="${loc}" key="blockUnblockUser" var="blockUnblockUser"/>

    <html>
      <head>
         <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <style>
          <%@include file="/WEB-INF/css/only_buttons.css"%>
        </style>
        <custom:hello userName="${hello}${current_user.first_name}" />
      </head>
      <body>
        <p></p>
        <ul class="wrapper">
        <form action="/controller?command=ADMIN_PAGE_COMMAND" method="post">
            <li class="form-row">
            <input type="submit" name="btnShowAllCourses" value="${showCourses}" />
          </li>
          <li class="form-row">
           <input type="submit" name="btnShowAllUsers" value="${showUsers}"  />
          </li>
           <li class="form-row">
          <input type="submit" name="btnShowAllReviews" value="${showReviews}"  />
          </li>
         <li class="form-row">
          <input type="submit" name="btnCreateNewTeacher" value="${createTeacher}"  />
          </li>
           <li class="form-row">
           <input type="submit" name="btnCreateNewGroup" value="${createGroup}"  />
           </li>
         <li class="form-row">
          <input type="submit" name="btnBlockUser" value="${blockUnblockUser}"  />
          </li>
         </ul>
        </form>
        <a href="/controller?command=LOG_OUT_COMMAND">
          <span class="glyphicon glyphicon-log-out" ></span>
        </a>
        <p></p>
         <%@ include file="footer/footer.jsp" %>
      </body>
    </html>