<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/hello_tag.tld" prefix="custom"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <html>
      <head>
        <link
          href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          rel="stylesheet"
          id="bootstrap-css"
        />
        <style>
          <%@include file="/WEB-INF/css/only_buttons.css"%>
        </style>
        <custom:hello userName="${current_user.first_name}" />
      </head>
      <body>
        <p></p>
        <form action="/controller?command=ADMIN_PAGE_COMMAND" method="post">
          <b type="submit" name="btnShowAllCourses" value="Show courses" />
          <input type="submit" name="btnShowAllUsers" value="Show users" />
          <input type="submit" name="btnShowAllReviews" value="Show reviews" />
          <input type="submit" name="btnCreateNewTeacher" value="Create teacher" />
          <input type="submit" name="btnCreateNewGroup" value="Create group" />
          <input type="submit" name="btnBlockUser" value="Block/Unblock user" />
        </form>
        <a href="/controller?command=LOG_OUT_COMMAND">Log out</a>
        <p></p>
        <%@ include file="footer/footer.jsp" %>
      </body>
    </html>