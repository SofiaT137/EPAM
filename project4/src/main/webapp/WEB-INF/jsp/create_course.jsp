<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="createCourse" var="createCourse"/>
<fmt:message bundle="${loc}" key="yourCourses" var="yourCourses"/>
<fmt:message bundle="${loc}" key="noCourses" var="noCourses"/>
<fmt:message bundle="${loc}" key="courseName" var="courseName"/>
<fmt:message bundle="${loc}" key="courseStartDate" var="courseStartDate"/>
<fmt:message bundle="${loc}" key="CourseEndDate" var="CourseEndDate"/>
<fmt:message bundle="${loc}" key="btnAddCourse" var="btnAddCourse"/>
<fmt:message bundle="${loc}" key="getBack" var="getBack"/>

<html>
   <head>
       <h2>${createCourse}</h2>
        </head>
         <body>
           <h2>${yourCourses}</h2>
    <c:choose>
              <c:when test="${user_course.size() eq 0}">
              <p>${noCourses}</p>
                  </c:when>
                  <c:otherwise>
          <table border="1">
               <thead>
                 <tr>
                <th>${courseName}</th>
                <th>${courseStartDate}</th>
                <th>${CourseEndDate}</th>
                   </tr>
               </thead>
                <c:forEach items="${requestScope.user_course}" var="course">
               <tbody>
                 <tr>
              <td><c:out value="${course.name}" /></td>
              <td><c:out value="${course.startCourse}" /></td>
              <td><c:out value="${course.endCourse}" /></td>
               </tr>
               </tbody>
             </c:forEach>
         </table>
            </c:otherwise>
            </c:choose>
            <p></p>
    <form action="/controller?command=CREATE_COURSE_COMMAND" method="post">
      <div class="form-group">
             <label>${courseName}</label>
             <input name="lblCourseName" type="text" placeholder="${CourseNameLabel}" required pattern="^[a-zA-Zа-яА-я '+.-]{2,30}$"/>
           </div>
           <p></p>
               <div class="form-group">
             <label>${courseStartDate}</label>
             <input name="lblStartDate" type="date" placeholder="${StartDateLabel}" required pattern = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$" />
              </div>
           <p></p>
           <div class="form-group">
            <label>${CourseEndDate}</label>
           <input name="lblEndDate" type="date" placeholder="${EndDateLabel}" required pattern = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$" />
           </div>
            <p></p>

            <button type="submit" name="btnAddCourse">${btnAddCourse}</button>
            <button type="submit" name="btnGetBack" onClick='location.href="/controller?command=SHOW_TEACHER_PAGE_COMMAND"'>${getBack}</button>
             </form>
              <a href = "/controller?command=LOG_OUT_COMMAND">Log out</a>
              </body>
              <p></p>
            <%@ include file="footer/footer.jsp" %>
        </body>
    </html>