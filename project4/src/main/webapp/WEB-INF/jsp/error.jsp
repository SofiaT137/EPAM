<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tld/error_tag.tld" prefix="err"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <%@ include file="header/header.jsp" %>
    <style>
        <%@include file="/WEB-INF/css/error.css"%>
    </style>
</head>

<body>
    <p>
    <div class="tagStyle">
        <err:error errorName="${error_name}" />
    </div>
    </p>
        <div class="imageicon"><img src="${pageContext.request.contextPath}/img/no_meme.jpg" alt="Grumpy cat picture" />
        </div>
        <p></p>
    <%@ include file="footer/footer.jsp" %>
</body>

</html>

