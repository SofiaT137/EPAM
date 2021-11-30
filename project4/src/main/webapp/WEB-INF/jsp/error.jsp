<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tld/error_tag.tld" prefix="err"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
       <head>
           <style>
             <%@include file="/WEB-INF/css/error.css"%>
           </style>
        </head>
        <body>
        <p>
        <err:error errorName="${error_name}"/>
        </p>
        <p></p>
         <a href = "/controller?command=LOG_OUT_COMMAND">Log out</a>
            <%@ include file="footer/footer.jsp" %>
            </body>
       </html>

