<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="createTeacher" var="createTeacher"/>
<fmt:message bundle="${loc}" key="allTeachers" var="allTeachers"/>
<fmt:message bundle="${loc}" key="noTeachers" var="noTeachers"/>
<fmt:message bundle="${loc}" key="userId" var="userId"/>
<fmt:message bundle="${loc}" key="firstName" var="firstName"/>
<fmt:message bundle="${loc}" key="lastName" var="lastName"/>
<fmt:message bundle="${loc}" key="Login" var="Login"/>
<fmt:message bundle="${loc}" key="Password" var="Password"/>
<fmt:message bundle="${loc}" key="btnAddTeacher" var="btnAddTeacher"/>
<fmt:message bundle="${loc}" key="LoginLabel" var="LoginLabel"/>
<fmt:message bundle="${loc}" key="PasswordLabel" var="PasswordLabel"/>
<fmt:message bundle="${loc}" key="FirstNameLabel" var="FirstNameLabel"/>
<fmt:message bundle="${loc}" key="LastNameLabel" var="LastNameLabel"/>
<fmt:message bundle="${loc}" key="getBack" var="getBack"/>
<fmt:message bundle="${loc}" key="invalidFirstName" var="invalidFirstName"/>
<fmt:message bundle="${loc}" key="invalidLastName" var="invalidLastName"/>
<fmt:message bundle="${loc}" key="invalidLogin" var="invalidLogin"/>
<fmt:message bundle="${loc}" key="invalidPassword" var="invalidPassword"/>

<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <%@ include file="header/header.jsp" %>
    <style>
        <%@include file="/WEB-INF/css/labels_buttons.css"%><%@include file="/WEB-INF/css/tables.css"%>
    </style>
</head>

<body>
    <h2>${createTeacher}</h2>
    <h4>${allTeachers}</h4>
    <c:choose>
        <c:when test="${all_teachers.size() eq 0}">
            <p>${noTeachers}</p>
        </c:when>
        <c:otherwise>
            <table border="1" table style="width:33%" style="text-align:center">
                <thead>
                    <tr>
                        <th>${userId}</th>
                        <th>${firstName}</th>
                        <th>${lastName}</th>
                    </tr>
                </thead>
                <c:forEach items="${requestScope.all_teachers}" var="teacher">
                    <tbody>
                        <tr>
                            <td>
                                <c:out value="${teacher.id}" />
                            </td>
                            <td>
                                <c:out value="${teacher.first_name}" />
                            </td>
                            <td>
                                <c:out value="${teacher.last_name}" />
                            </td>
                        </tr>
                    </tbody>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
    <p></p>
    <form action="/controller?command=CREATE_TEACHER_COMMAND" method="post">
        <div class="form-group">
            <label>${Login}</label>
            <input name="lblLogin" type="text" class="input" title="${invalidLogin}" placeholder="${enterYourLogin}" required pattern="^[A-Za-z0-9,.'-]{4,}$" />
        </div>
        <p></p>
        <div class="form-group">
            <label>${Password}</label>
            <input name="lblPassword" type="password" class="input" title="${invalidPassword}" placeholder="${enterYourPassword}" required pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d@$!%*#?&-]{8,}$" />
        </div>
        <p></p>
        <div class="form-group">
            <label>${firstName}</label>
            <input name="lblFirstName" type="text" title="${invalidFirstName}" placeholder="${FirstNameLabel}" required pattern="^[a-zA-Zа-яА-Я '.-]{2,20}$" />
        </div>
        <p></p>
        <div class="form-group">
            <label>${lastName}</label>
            <input name="lblLastName" type="text" title="${invalidLastName}" placeholder="${LastNameLabel}" required pattern="^[a-zA-Zа-яА-Я '.-]{2,20}$" />
        </div>
        <p></p>
        <button type="submit" name="btnAddTeacher">${btnAddTeacher}</button>
        <button type="submit" name="btnGetBack"
            onClick='location.href="/controller?command=SHOW_ADMIN_PAGE_COMMAND"'>${getBack}</button>
    </form>
    <p></p>
    <%@ include file="footer/footer.jsp" %>
</body>

</html>