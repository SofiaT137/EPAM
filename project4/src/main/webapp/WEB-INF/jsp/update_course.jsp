<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="letsUpdateACourse" var="letsUpdateACourse"/>
<fmt:message bundle="${loc}" key="yourCurrentCourses" var="yourCurrentCourses"/>
<fmt:message bundle="${loc}" key="noCourses" var="noCourses"/>
<fmt:message bundle="${loc}" key="courseId" var="courseId"/>
<fmt:message bundle="${loc}" key="courseName" var="courseName"/>
<fmt:message bundle="${loc}" key="courseStartDate" var="courseStartDate"/>
<fmt:message bundle="${loc}" key="CourseEndDate" var="CourseEndDate"/>
<fmt:message bundle="${loc}" key="newCourseData" var="newCourseData"/>
<fmt:message bundle="${loc}" key="StartDateLabel" var="StartDateLabel"/>
<fmt:message bundle="${loc}" key="EndDateLabel" var="EndDateLabel"/>
<fmt:message bundle="${loc}" key="btnUpdate" var="btnUpdate"/>
<fmt:message bundle="${loc}" key="getBack" var="getBack"/>


<html>
   <head>
        </head>
         <body>
           <h2>${letsUpdateACourse}</h2>
           <h4>${yourCurrentCourses}</h4>
    <c:choose>
              <c:when test="${user_course.size() eq 0}">
              <p>${noCourses}</p>
                  </c:when>
                  <c:otherwise>
          <table border="1">
               <thead>
                 <tr>
                   <th>${courseId}</th>
                   <th>${courseName}</th>
                   <th>${courseStartDate}</th>
                   <th>${CourseEndDate}</th>
                   </tr>
               </thead>
                <c:forEach items="${requestScope.user_course}" var="course">
               <tbody>
                 <tr>
              <td><c:out value="${course.id}" /></td>
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
         <form action="/controller?command=UPDATE_COURSE_COMMAND" method="post">
         <div class="form-group">
             <h3>${newCourseData}</h3>
              <label>${courseName}</label>
               <select name="Course_name">
               <c:forEach items="${requestScope.user_course}" var="course">
                 <option value="${course.name}">${course.name}</option>
                  </c:forEach>
               </select>
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
            <button type="submit" name="btnUpdate">${btnUpdate}</button>
            <button type="submit" name="btnGetBack" onClick='location.href="/controller?command=SHOW_TEACHER_PAGE_COMMAND"'>${getBack}</button>
              </form>
             <p></p>
              <a href = "/controller?command=LOG_OUT_COMMAND">Log out</a>
              </body>
        <div class="footer"><%@ include file="footer/footer.jsp" %></div>
     </html>