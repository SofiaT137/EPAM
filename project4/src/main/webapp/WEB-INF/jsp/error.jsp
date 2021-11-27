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
         <a href = "/controller?command=LOG_OUT_COMMAND">Log out</a>
            <%@ include file="footer/footer.jsp" %>
            </body>
       </html>

