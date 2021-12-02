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

<html>
   <head>
       <h2>${allTheUniversityUsers}</h2>
        </head>
         <body>
      <c:choose>
              <c:when test="${all_users.size() eq 0}">
              <p>${noUsers}</p>
                  </c:when>
                  <c:otherwise>
          <table border="1" table style="width:450px" style="text-align:center">
               <thead>
                <tr>
                  <th>${userId}</th>
                  <th>${accountId}</th>
                  <th>${groupName}</th>
                  <th>${firstName}</th>
                  <th>${lastName}</th>
                 </tr>
              </thead>
            <c:forEach items="${requestScope.all_users}" var="user">
           <tbody>
             <tr>
          <td><c:out value="${user.id}" /></td>
          <td><c:out value="${user.account_id}" /></td>
          <td><c:out value="${user.group_name}" /></td>
          <td><c:out value="${user.first_name}" /></td>
          <td><c:out value="${user.last_name}" /></td>
           </tr>
           </tbody>
               </c:forEach>
              </table>
                 </c:otherwise>
                 </c:choose>
                 <p></p>
                   <a href = "/controller?command=SHOW_ADMIN_PAGE_COMMAND">${getBack}</a>
                 <%@ include file="footer/footer.jsp" %>
              </body>
           </html>