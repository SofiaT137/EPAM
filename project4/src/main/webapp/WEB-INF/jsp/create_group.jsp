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

<html>
   <head>
         <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <style>
            <%@include file="/WEB-INF/css/labels_buttons_tables.css"%>
            <%@include file="/WEB-INF/css/tables.css"%>
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
              <td><c:out value="${group.id}" /></td>
              <td><c:out value="${group.name}" /></td>
               </tr>
               </tbody>
             </c:forEach>
         </table>
            </c:otherwise>
            </c:choose>
            <p></p>
    <form action="/controller?command=CREATE_GROUP_COMMAND" method="post">
      <div class="form-group">
             <label>${groupName}</label>
             <input name="lblGroupName" type="text" placeholder="${GroupNameLabel}" required pattern="^[A-Za-zа-яА-Я0-9 ,.'-]{3,20}$" />
           </div>
           <p></p>
            <button type="submit" name="btnAddGroup">${btnAddGroup}</button>
            <button type="submit" name="btnGetBack" onClick='location.href="/controller?command=SHOW_ADMIN_PAGE_COMMAND"'>${getBack}</button>
             </form>
           <div class="logout">
           <a href="/controller?command=LOG_OUT_COMMAND">
             <span class="glyphicon glyphicon-log-out" ></span>
           </a>
           </div>
           <p></p>
                <%@ include file="footer/footer.jsp" %>
            </body>
        </html>