<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="createGroup" var="createGroup"/>
<fmt:message bundle="${loc}" key="allGroups" var="allGroups"/>
<fmt:message bundle="${loc}" key="noGroups" var="noGroups"/>
<fmt:message bundle="${loc}" key="groupId" var="groupId"/>
<fmt:message bundle="${loc}" key="groupName" var="groupName"/>
<fmt:message bundle="${loc}" key="GroupNameLabel" var="GroupNameLabel"/>
<fmt:message bundle="${loc}" key="btnAddGroup" var="btnAddGroup"/>
<fmt:message bundle="${loc}" key="getBack" var="getBack"/>
<fmt:message bundle="${loc}" key="invalidGroupName" var="invalidGroupName"/>
<fmt:message bundle="${loc}" key="next" var="next"/>
<fmt:message bundle="${loc}" key="previous" var="previous"/>
<fmt:message bundle="${loc}" key="invalidUniqueness" var="invalidUniqueness"/>

<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <%@ include file="header/header.jsp" %>
    <style>
        <%@include file="/WEB-INF/css/labels_buttons.css"%><%@include file="/WEB-INF/css/tables.css"%>
    </style>
</head>

<body>
    <h2>${createGroup}</h2>
    <h4>${allGroups}</h4>
    <c:choose>
        <c:when test="${university_groups.size() eq 0}">
            <p>${noGroups}</p>
        </c:when>
        <c:otherwise>
            <table border="1" table style="width:33%" style="text-align:center">
                <thead>
                    <tr>
                        <th>${groupId}</th>
                        <th>${groupName}</th>
                    </tr>
                </thead>
                <c:forEach items="${requestScope.university_groups}" var="group">
                    <tbody>
                        <tr>
                            <td>
                                <c:out value="${group.id}" />
                            </td>
                            <td>
                                <c:out value="${group.name}" />
                            </td>
                        </tr>
                    </tbody>
                </c:forEach>
            </table>
                    <div class="paggination">
                       <c:if test="${current_page != 1}">
                           <td>
                               <a href="/controller?command=SHOW_CREATE_GROUP_PAGE_COMMAND&page=${current_page - 1}">${previous}</a>
                           </td>
                       </c:if>

                       <c:forEach begin="1" end="${number_of_pages}" var="i">
                           <c:choose>
                               <c:when test="${current_page eq i}">
                                   <td>${i}</td>
                               </c:when>
                               <c:otherwise>
                                   <td>
                                       <a href="/controller?command=SHOW_CREATE_GROUP_PAGE_COMMAND&page=${i}">${i}</a>
                                   </td>
                               </c:otherwise>
                           </c:choose>
                       </c:forEach>

                       <%--For displaying Next link --%>
                           <c:if test="${current_page lt number_of_pages}">
                               <td>
                                   <a href="/controller?command=SHOW_CREATE_GROUP_PAGE_COMMAND&page=${current_page + 1}">${next}</a>
                               </td>
                           </c:if>
                   </div>
               </c:otherwise>
           </c:choose>
           <p></p>
    <form action="/controller?command=CREATE_GROUP_COMMAND" method="post">
        <div class="form-group">
            <label>${groupName}</label>
            <input name="lblGroupName" type="text" placeholder="${GroupNameLabel}" title="${invalidGroupName}" required pattern="^[A-Za-zа-яА-Я0-9 ,.'-]{3,20}$" />
                 <c:if test="${not_unique eq 'This group name is not unique!'}">
                        <p>${invalidUniqueness}</p>
                </c:if>
        </div>
        <p></p>
        <button type="submit" name="btnAddGroup">${btnAddGroup}</button>
        <button type="submit" name="btnGetBack" onClick='location.href="/controller?command=SHOW_ADMIN_PAGE_COMMAND"'>${getBack}</button>
    </form>
    <p></p>
    <%@ include file="footer/footer.jsp" %>
</body>

</html>