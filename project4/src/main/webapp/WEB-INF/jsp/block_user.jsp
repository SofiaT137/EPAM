<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="blockUser" var="blockUser"/>
<fmt:message bundle="${loc}" key="allBlockedUser" var="allBlockedUser"/>
<fmt:message bundle="${loc}" key="noBlockedUsers" var="noBlockedUsers"/>
<fmt:message bundle="${loc}" key="userId" var="userId"/>
<fmt:message bundle="${loc}" key="firstName" var="firstName"/>
<fmt:message bundle="${loc}" key="lastName" var="lastName"/>
<fmt:message bundle="${loc}" key="Group" var="Group"/>
<fmt:message bundle="${loc}" key="btnUnBlockUser" var="btnUnBlockUser"/>
<fmt:message bundle="${loc}" key="btnBlockUser" var="btnBlockUser"/>
<fmt:message bundle="${loc}" key="FirstLabel" var="FirstLabel"/>
<fmt:message bundle="${loc}" key="LastLabel" var="LastLabel"/>
<fmt:message bundle="${loc}" key="getBack" var="getBack"/>

<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
    <style>
        <%@include file="/WEB-INF/css/labels_buttons.css"%><%@include file="/WEB-INF/css/tables.css"%>
    </style>
</head>

<body>
    <h2>${blockUser}</h2>
    <h4>${allBlockedUser}</h4>
    <c:choose>
        <c:when test="${blocked_users.size() eq 0}">
            <p>${noBlockedUsers}</p>
        </c:when>
        <c:otherwise>
            <table border="1" table style="width: 33%" style="text-align: center">
                <thead>
                    <tr>
                        <th>${userId}</th>
                        <th>${firstName}</th>
                        <th>${lastName}</th>
                    </tr>
                </thead>
                <c:forEach items="${requestScope.blocked_users}" var="user">
                    <tbody>
                        <tr>
                            <td>
                                <c:out value="${user.id}" />
                            </td>
                            <td>
                                <c:out value="${user.first_name}" />
                            </td>
                            <td>
                                <c:out value="${user.last_name}" />
                            </td>
                        </tr>
                    </tbody>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
    <p></p>
    <form action="/controller?command=BLOCK_USER_COMMAND" method="post">
        <div class="form-group">
            <label>${firstName}</label>
            <input name="lblFirstName" type="text" placeholder="${FirstLabel}" required
                pattern="^[a-zA-Zа-яА-Я '.-]{2,20}$" />
        </div>
        <p></p>
        <div class="form-group">
            <label>${lastName}</label>
            <input name="lblLastName" type="text" placeholder="${LastLabel}" required
                pattern="^[a-zA-Zа-яА-Я '.-]{2,20}$" />
        </div>
        <p></p>
        <label>${Group}</label>
        <select name="Group">
            <c:forEach items="${requestScope.university_groups}" var="group">
                <option value="${group.name}">${group.name}</option>
            </c:forEach>
        </select>
        <p></p>
        <button type="submit" name="btnUnBlockUser">${btnUnBlockUser}</button>
        <button type="submit" name="btnBlockUser">${btnBlockUser}</button>
        <button type="submit" name="btnGetBack" onClick='location.href="/controller?command=SHOW_ADMIN_PAGE_COMMAND"'>
            ${getBack}
        </button>
    </form>
    <div class="logout">
        <a href="/controller?command=LOG_OUT_COMMAND">
            <span class="glyphicon glyphicon-log-out"></span>
        </a>
    </div>
    <p></p>
    <%@ include file="footer/footer.jsp" %>
</body>

</html>
