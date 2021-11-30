<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="Elective" var="Elective"/>
<fmt:message bundle="${loc}" key="courseStudents" var="courseStudents"/>
<fmt:message bundle="${loc}" key="noCourseStudent" var="noCourseStudent"/>
<fmt:message bundle="${loc}" key="groupNumber" var="groupNumber"/>
<fmt:message bundle="${loc}" key="firstName" var="firstName"/>
<fmt:message bundle="${loc}" key="lastName" var="lastName"/>
<fmt:message bundle="${loc}" key="getBack" var="getBack"/>
<fmt:message bundle="${loc}" key="FirstNameLabel" var="FirstNameLabel"/>
<fmt:message bundle="${loc}" key="LastNameLabel" var="LastNameLabel"/>
<fmt:message bundle="${loc}" key="GroupLabel" var="GroupLabel"/>
<fmt:message bundle="${loc}" key="GradeLabel" var="GradeLabel"/>
<fmt:message bundle="${loc}" key="ReviewLabel" var="ReviewLabel"/>
<fmt:message bundle="${loc}" key="Grade" var="Grade"/>
<fmt:message bundle="${loc}" key="Review" var="Review"/>
<fmt:message bundle="${loc}" key="btnAddReview" var="btnAddReview"/>


<html>
   <head>
       <title>${Elective}</title>
        </head>
   <body>
   <h2>${courseStudents}</h2>
   <c:choose>
           <c:when test="${students_course.size() eq 0}">
           <p>${noCourseStudent}</p>
               </c:when>
               <c:otherwise>
       <table border="1">
            <thead>
              <tr>
                <th>${groupNumber}</th>
                <th>${firstName}</th>
                <th>${lastName}</th>
                </tr>
            </thead>
             <c:forEach items="${requestScope.students_course}" var="student">
            <tbody>
              <tr>
                  <td><c:out value="${student.group_id}" /></td>
                  <td><c:out value="${student.first_name}" /></td>
                   <td><c:out value="${student.last_name}" /></td>
                </tr>
            </tbody>
          </c:forEach>
      </table>
         </c:otherwise>
         </c:choose>
         <p></p>
           <form action="/controller?command=RATE_STUDENT_COMMAND" method="post">
              <div class="form-group">
                     <label>${firstName}</label>
                     <input name="lblFirstName" type="text" placeholder="${FirstNameLabel}" required pattern="^[a-zA-Zа-яА-Я '.-]{2,20}$" />
                   </div>
                   <p></p>
                       <div class="form-group">
                     <label>${lastName}</label>
                     <input name="lblLastName" type="text" placeholder="${LastNameLabel}" required pattern="^[a-zA-Zа-яА-Я '.-]{2,20}$" />
                      </div>
                   <p></p>
                   <div class="form-group">
                    <label>${groupNumber}</label>
                   <input name="lblGroup" type="text" placeholder="${GroupLabel}" required pattern="^[A-Za-zа-яА-Я0-9 ,.'-]*$" />
                   </div>
                    <p></p>
                  <div class="form-group">
                    <label>${Grade}</label>
                     <input name="lblGrade" type="number" placeholder="${GradeLabel}" min="0" max="10"/>
                     </div>
                     <p></p>
                     <div class="form-group">
                  <label>${Review}</label>
                     <input name="lblReview" type="text" placeholder="${ReviewLabel}" />
                     </div>
                 <p></p>
            <button type="submit" name="btnAddReview"  <c:if test="${students_course.size() == 0}"><c:out value="disabled='disabled'"/></c:if>>${btnAddReview}</button>
            <button type="submit" name="btnGetBack" onClick='location.href="/controller?command=SHOW_TEACHER_PAGE_COMMAND"'>${getBack}</button>
                     </form>
                <a href = "/controller?command=LOG_OUT_COMMAND">Log out</a>
                <%@ include file="footer/footer.jsp" %>
            </body>
        </html>