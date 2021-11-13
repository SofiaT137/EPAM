<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tld/hello_tag.tld" prefix="custom"%>
<html>
   <head>
        <custom:hello userName="${current_user.first_name} ${current_user.last_name}"/>
        </head>
    <body>

    <footer class="bg-light text-center text-lg-start">
        <div class="text-center p-3" style="background-color: rgba(88, 69, 16, 0.2);">
         © 2021 Copyright: Made by Sofia Tkachenia
       </div>
     </footer>
    </body>
</html>