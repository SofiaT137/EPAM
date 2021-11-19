<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tld/error_tag.tld" prefix="err"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
   <head>
        <err:error errorName="${error_name}"/>
        </head>
        <p></p>
        <body>
        <img src="/img/no_meme.jpg" width="250" height="250" alt="This is an image" />
        <p></p>
         <a href = "/controller?command=SHOW_USER_PAGE_COMMAND">Log out</a>
        <footer class="bg-light text-center text-lg-start">
               <div class="text-center p-3" style="background-color: rgba(88, 69, 16, 0.2);">
                © 2021 Copyright: Made by Sofia Tkachenia
              </div>
            </footer>
            </body>
       </html>

