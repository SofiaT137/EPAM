<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="elective" var="elective"/>
<fmt:message bundle="${loc}" key="letsFinishRegistration" var="letsFinishRegistration"/>
<fmt:message bundle="${loc}" key="firstName" var="firstName"/>
<fmt:message bundle="${loc}" key="lastName" var="lastName"/>
<fmt:message bundle="${loc}" key="FirstNameLabel" var="FirstNameLabel"/>
<fmt:message bundle="${loc}" key="LastNameLabel" var="LastNameLabel"/>
<fmt:message bundle="${loc}" key="Group" var="Group"/>
<fmt:message bundle="${loc}" key="btnRegister" var="btnRegister"/>


<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <style>
        <%@include file="/WEB-INF/css/labels_buttons.css"%><%@include file="/WEB-INF/css/tables.css"%>
    </style>
</head>

<body>
    <h2>${letsFinishRegistration}</h2>
    <form action="/controller?command=REGISTER_USER_COMMAND" method="post">
        <div class="form-group">
            <label>${firstName}</label>
            <input name="lblFirstName" type="text" placeholder="${FirstNameLabel}" required
                pattern="^[a-zA-Z '.-]{2,20}*$" />
        </div>
        <p></p>
        <div class="form-group">
            <label>${lastName}</label>
            <input name="lblLastName" type="text" placeholder="${LastNameLabel}" required
                pattern="^[a-zA-Z '.-]{2,20}*$" />
        </div>
        <p></p>
        <div class="form-group">
            <label>${Group}</label>
            <select name="group_name">
                <c:forEach items="${requestScope.all_groups}" var="group">
                    <option value="${group.name}">${group.name}</option>
                </c:forEach>
            </select>
        </div>
        <p></p>
        <button type="submit" name="btnRegister">${btnRegister}</button>
    </form>
    <p></p>
    <a href="/controller?command=LOG_OUT_COMMAND">Log out</a>
</body>
<%@ include file="footer/footer.jsp" %>
    </body>

</html>