<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib uri="/WEB-INF/tld/hello_tag.tld" prefix="custom" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <fmt:setLocale value="${sessionScope.language}" />
        <fmt:setBundle basename="locale" var="loc" />

        <fmt:message bundle="${loc}" key="hello" var="hello" />
        <fmt:message bundle="${loc}" key="showCourses" var="showCourses" />
        <fmt:message bundle="${loc}" key="showUsers" var="showUsers" />
        <fmt:message bundle="${loc}" key="showReviews" var="showReviews" />
        <fmt:message bundle="${loc}" key="createTeacher" var="createTeacher" />
        <fmt:message bundle="${loc}" key="createGroup" var="createGroup" />
        <fmt:message bundle="${loc}" key="blockUnblockUser" var="blockUnblockUser" />

        <!DOCTYPE HTML>
        <html>

        <head>
          <title>Admin page</title>
          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
          <style>
            <%@include file="/WEB-INF/css/only_buttons.css"%><%@include file="/WEB-INF/css/labels_buttons.css"%>
          </style>
        </head>

        <body>
          <div class="custom">
            <custom:hello userName="${hello} ${current_user.firstName}" />
          </div>
          <div class="wrapper">
            <div class="center">
              <form action="/controller?command=ADMIN_PAGE_COMMAND" method="post">
                <button type="submit" name="btnShowAllCourses">${showCourses}</button>
                <button type="submit" name="btnShowAllUsers">${showUsers}</button>
                <button type="submit" name="btnShowAllReviews">${showReviews}</button>
                <button type="submit" name="btnCreateNewTeacher">${createTeacher}</button>
                <button type="submit" name="btnCreateNewGroup">${createGroup}</button>
                <button type="submit" name="btnBlockUser">${blockUnblockUser}</button>
              </form>
            </div>
          </div>
          <div class="logout">
            <a href="/controller?command=LOG_OUT_COMMAND">
              <span class="glyphicon glyphicon-log-out"></span>
            </a>
          </div>
          <p></p>
          <%@ include file="footer/footer.jsp" %>
        </body>

        </html>