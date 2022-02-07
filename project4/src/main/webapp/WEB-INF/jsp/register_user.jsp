<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="elective" var="elective"/>
<fmt:message bundle="${loc}" key="letsFinishRegistration" var="letsFinishRegistration"/>
<fmt:message bundle="${loc}" key="firstName" var="firstName"/>
<fmt:message bundle="${loc}" key="lastName" var="lastName"/>
<fmt:message bundle="${loc}" key="firstNameLabel" var="firstNameLabel"/>
<fmt:message bundle="${loc}" key="lastNameLabel" var="lastNameLabel"/>
<fmt:message bundle="${loc}" key="Group" var="Group"/>
<fmt:message bundle="${loc}" key="btnRegister" var="btnRegister"/>
<fmt:message bundle="${loc}" key="invalidFirstName" var="invalidFirstName"/>
<fmt:message bundle="${loc}" key="invalidLastName" var="invalidLastName"/>
<fmt:message bundle="${loc}" key="error" var="error"/>


<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <%@ include file="header/header.jsp" %>
    <style>
        <%@include file="/WEB-INF/css/labels_buttons.css"%><%@include file="/WEB-INF/css/tables.css"%>
    </style>
</head>

<body>
    <div class="wrapper">
    <h2>${letsFinishRegistration}</h2>
    <p></p>
    <form action="/controller?command=REGISTER_USER_COMMAND" method="post">
        <div class="form-group">
            <label>${firstName}</label>
            <input name="lblFirstName" type="text" title="${invalidFirstName}" placeholder="${firstNameLabel}" required pattern="^[a-zA-Zа-яА-Я '.-]{2,20}$" />
        </div>
        <p></p>
        <div class="form-group">
            <label>${lastName}</label>
            <input name="lblLastName" type="text" title="${invalidLastName}" placeholder="${lastNameLabel}" required pattern="^[a-zA-Zа-яА-Я '.-]{2,20}$" />
        </div>
        <p></p>
        <div class="form-group">
            <label>${Group}</label>
            <input name = "groupName" list = "groups" placeholder = "Select group name" autocomplete="on" />
            <datalist id = "groups">
                <c:forEach items="${requestScope.all_groups}" var="group">
                    <option value="${group.name}">${group.name}</option>
                </c:forEach>
            </datalist>
        </div>
        <p></p>
        <div class="invalid">
            <c:if test="${errorMsg ne null}">
                <p>${error}: ${errorMsg}</p>
            </c:if>
        </div>
        <button type="submit" name="btnRegister">${btnRegister}</button>
    </form>
    <p></p>
        <%@ include file="footer/footer.jsp" %>
        </div>
    </body>
</html>