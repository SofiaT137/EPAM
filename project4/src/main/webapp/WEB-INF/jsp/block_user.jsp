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
<fmt:message bundle="${loc}" key="groupName" var="groupName"/>
<fmt:message bundle="${loc}" key="Group" var="Group"/>
<fmt:message bundle="${loc}" key="btnUnBlockUser" var="btnUnBlockUser"/>
<fmt:message bundle="${loc}" key="btnBlockUser" var="btnBlockUser"/>
<fmt:message bundle="${loc}" key="FirstLabel" var="FirstLabel"/>
<fmt:message bundle="${loc}" key="LastLabel" var="LastLabel"/>
<fmt:message bundle="${loc}" key="getBack" var="getBack"/>
<fmt:message bundle="${loc}" key="invalidFirstName" var="invalidFirstName"/>
<fmt:message bundle="${loc}" key="invalidLastName" var="invalidLastName"/>
<fmt:message bundle="${loc}" key="next" var="next"/>
<fmt:message bundle="${loc}" key="previous" var="previous"/>
<fmt:message bundle="${loc}" key="cannotFindUserByFirstAndLastName" var="cannotFindUserByFirstAndLastName"/>
<fmt:message bundle="${loc}" key="cannotFindThisUserInGroup" var="cannotFindThisUserInGroup"/>
<fmt:message bundle="${loc}" key="accountBlocked" var="accountBlocked"/>
<fmt:message bundle="${loc}" key="accountUnblocked" var="accountUnblocked"/>
<fmt:message bundle="${loc}" key="error" var="error"/>

<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
    <%@ include file="header/header.jsp" %>
        <style>
            <%@include file="/WEB-INF/css/labels_buttons.css"%><%@include file="/WEB-INF/css/tables.css"%>
        </style>
</head>

<body>
    <h2>${blockUser}</h2>
    <h3>${allBlockedUser}</h3>
    <c:choose>
        <c:when test="${blocked_users.size() eq 0}">
            <div class="exception">
                <p>${noBlockedUsers}</p>
            </div>
        </c:when>
        <c:otherwise>
            <table border="1" table style="width: 33%" style="text-align: center">
                <thead>
                    <tr>
                        <th>${firstName}</th>
                        <th>${lastName}</th>
                        <th>${groupName}</th>
                    </tr>
                </thead>
                <c:forEach items="${requestScope.blocked_users}" var="user">
                    <tbody>
                        <tr>
                            <td>
                                <c:out value="${user.firstName}" />
                            </td>
                            <td>
                                <c:out value="${user.lastName}" />
                            </td>
                            <td>
                                <c:out value="${user.groupName}" />
                            </td>
                        </tr>
                    </tbody>
                </c:forEach>
            </table>
            <div class="paggination">
                <c:if test="${current_page != 1}">
                    <td>
                        <a
                            href="/controller?command=SHOW_BLOCK_USER_PAGE_COMMAND&page=${current_page - 1}">${previous}</a>
                    </td>
                </c:if>

                <c:forEach begin="1" end="${number_of_pages}" var="i">
                    <c:choose>
                        <c:when test="${current_page eq i}">
                            <td>${i}</td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <a href="/controller?command=SHOW_BLOCK_USER_PAGE_COMMAND&page=${i}">${i}</a>
                            </td>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <%--For displaying Next link --%>
                    <c:if test="${current_page lt number_of_pages}">
                        <td>
                            <a
                                href="/controller?command=SHOW_BLOCK_USER_PAGE_COMMAND&page=${current_page + 1}">${next}</a>
                        </td>
                    </c:if>
            </div>
        </c:otherwise>
    </c:choose>
    <p></p>
    <form action="/controller?command=BLOCK_USER_COMMAND" method="post">
        <div class="form-group">
            <label>${firstName}</label>
            <input name="lblFirstName" type="text" title="${invalidFirstName}" placeholder="${FirstNameLabel}" required
                pattern="^[a-zA-Zа-яА-Я '.-]{2,20}$" />
        </div>
        <p></p>
        <div class="form-group">
            <label>${lastName}</label>
            <input name="lblLastName" type="text" title="${invalidLastName}" placeholder="${LastNameLabel}" required
                pattern="^[a-zA-Zа-яА-Я '.-]{2,20}$" />
        </div>
        <p></p>
        <label>${Group}</label>
        <div class="form-group">
        <select name="lblGroup">
            <c:forEach items="${requestScope.university_groups}" var="group">
                <option value="${group.name}">${group.name}</option>
            </c:forEach>
        </select>
        </div>
        <p></p>
        <div class="invalid">
            <c:choose>
                <c:when test="${errorMsg eq 'cannotFindUserByFirstAndLastName'}">
                    <p>${error}:${cannotFindUserByFirstAndLastName}</p>
                </c:when>
                <c:when test="${errorMsg eq 'cannotFindThisUserInGroup'}">
                    <p>${error}:${cannotFindThisUserInGroup}</p>
                </c:when>
                <c:when test="${errorMsg eq 'accountBlocked'}">
                    <p>${error}:${accountBlocked}</p>
                </c:when>
                <c:when test="${errorMsg eq 'accountUnblocked'">
                    <p>${error}:${accountUnblocked}</p>
                </c:when>
                <c:otherwise>
                    <c:if test="${errorMsg ne null}">
                        <p>${error}:${errorMsg}</p>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
        <button type="submit" name="btnUnBlockUser">${btnUnBlockUser}</button>
        <button type="submit" name="btnBlockUser">${btnBlockUser}</button>
        <button type="submit" name="btnGetBack" onClick='location.href="/controller?command=SHOW_ADMIN_PAGE_COMMAND"'>${getBack}</button>
    </form>
    <p></p>
    <%@ include file="footer/footer.jsp" %>
</body>

</html>