<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="allTheUniversityUsers" var="allTheUniversityUsers"/>
<fmt:message bundle="${loc}" key="noUsers" var="noUsers"/>
<fmt:message bundle="${loc}" key="userId" var="userId"/>
<fmt:message bundle="${loc}" key="accountId" var="accountId"/>
<fmt:message bundle="${loc}" key="groupName" var="groupName"/>
<fmt:message bundle="${loc}" key="firstName" var="firstName"/>
<fmt:message bundle="${loc}" key="lastName" var="lastName"/>
<fmt:message bundle="${loc}" key="getBack" var="getBack"/>
<fmt:message bundle="${loc}" key="next" var="next"/>
<fmt:message bundle="${loc}" key="previous" var="previous"/>

<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
    <%@ include file="header/header.jsp" %>
    <style>
        <%@include file="/WEB-INF/css/tables.css"%>
        <%@include file="/WEB-INF/css/labels_buttons.css"%>
    </style>
</head>

<body>
    <div class="wrapper">
    <h2>${allTheUniversityUsers}</h2>
    <c:choose>
        <c:when test="${all_users.size() eq 0}">
            <p>${noUsers}</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="5" cellspacing="5" table style="width: 33%" style="text-align: center">
                <thead>
                    <tr>
                        <th>${groupName}</th>
                        <th>${firstName}</th>
                        <th>${lastName}</th>
                    </tr>
                </thead>
                <c:forEach items="${requestScope.all_users}" var="user">
                    <tbody>
                        <tr>
                            <td>
                                <c:out value="${user.groupName}" />
                            </td>
                            <td>
                                <c:out value="${user.firstName}" />
                            </td>
                            <td>
                                <c:out value="${user.lastName}" />
                            </td>
                        </tr>
                    </tbody>
                </c:forEach>
            </table>
            <div class="paggination">
                <c:if test="${current_page != 1}">
                    <td>
                        <a href="/controller?command=SHOW_ALL_USER&page=${current_page - 1}">${previous}</a>
                    </td>
                </c:if>

                <c:forEach begin="1" end="${number_of_pages}" var="i">
                    <c:choose>
                        <c:when test="${current_page eq i}">
                            <td>${i}</td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <a href="/controller?command=SHOW_ALL_USER&page=${i}">${i}</a>
                            </td>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <%--For displaying Next link --%>
                    <c:if test="${current_page lt number_of_pages}">
                        <td>
                            <a href="/controller?command=SHOW_ALL_USER&page=${current_page + 1}">${next}</a>
                        </td>
                    </c:if>
            </div>
        </c:otherwise>
    </c:choose>
    <div class="getBack">
        <a href="/controller?command=SHOW_ADMIN_PAGE_COMMAND">${getBack}</a>
    </div>
    <%@ include file="footer/footer.jsp" %>
    </div>
</body>

</html>
