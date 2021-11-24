<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
   <head>
       <h2>Let`s create a group: </h2>
        </head>
         <body>
           <h2>All university groups: </h2>
    <c:choose>
              <c:when test="${university_groups.size() eq 0}">
              <p>You have no university groups!</p>
                  </c:when>
                  <c:otherwise>
          <table border="1">
               <thead>
                 <tr>
                   <th>Group id</th>
                    <th>Group name</th>
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
             <label>Group name: name:</label>
             <input name="lblGroupName" type="text" placeholder="${GroupNameLabel}" />
           </div>
           <p></p>
             <input type="submit" name="btnAddGroup" value="Add group"/>
             <input type="submit" name="btnGetBack" value="Get Back" />
             </form>
             <a href = "/controller?command=LOG_OUT_COMMAND">Log out</a>
              </body>
              <p></p>
     <footer class="bg-light text-center text-lg-start">
         <div class="text-center p-3" style="background-color: rgba(88, 69, 16, 0.2);">
          © 2021 Copyright: Made by Sofia Tkachenia
        </div>
      </footer>
     </html>