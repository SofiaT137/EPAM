<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tld/hello_tag.tld" prefix="custom"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
   <head>
        <custom:hello userName="${current_user.first_name}"/>
        </head>
     <body>
            <p></p>
          <form action="/controller?command=ADMIN_PAGE_COMMAND" method="post">
                   <input type="submit" name="btnShowAllCourses" value="Show courses" />
                   <input type="submit" name="btnShowAllUsers" value="Show users" />
                   <input type="submit" name="btnShowAllReviews" value="Show reviews" />
                   <input type="submit" name="btnCreateNewTeacher" value="Create teacher" />
                   <input type="submit" name="btnCreateNewGroup" value="Create group" />
                   <input type="submit" name="btnBlockUser" value="Block user" />
                   </form>
                   <a href = "/controller?command=LOG_OUT_COMMAND">Log out</a>
             <p></p>
     <footer class="bg-light text-center text-lg-start">
         <div class="text-center p-3" style="background-color: rgba(88, 69, 16, 0.2);">
          © 2021 Copyright: Made by Sofia Tkachenia
        </div>
      </footer>
     </body>
     </html>