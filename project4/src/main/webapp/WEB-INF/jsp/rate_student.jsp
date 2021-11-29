<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
   <head>
       <title>Elective</title>
       <h2>Let`s finish this review:</h2>
        </head>
   <body>
   <h2>This course students: </h2>
   <c:choose>
           <c:when test="${students_course.size() eq 0}">
           <p>You have no students on this courses!</p>
               </c:when>
               <c:otherwise>
       <table border="1">
            <thead>
              <tr>
                <th>Group number</th>
                <th>First name</th>
                <th>Last name</th>
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
                     <label>Student first name:</label>
                     <input name="lblFirstName" type="text" placeholder="${FirstNameLabel}" required pattern="^[a-zA-Zа-яА-Я '.-]{2,20}$" />
                   </div>
                   <p></p>
                       <div class="form-group">
                     <label>Student last name:</label>
                     <input name="lblLastName" type="text" placeholder="${LastNameLabel}" required pattern="^[a-zA-Zа-яА-Я '.-]{2,20}$" />
                      </div>
                   <p></p>
                   <div class="form-group">
                    <label>Student group:</label>
                   <input name="lblGroup" type="text" placeholder="${GroupLabel}" required pattern="^[A-Za-zа-яА-Я0-9 ,.'-]*$" />
                   </div>
                    <p></p>
                  <div class="form-group">
                    <label>Student grade:</label>
                     <input name="lblGrade" type="number" placeholder="${GradeLabel}" min="0" max="10"/>
                     </div>
                     <p></p>
                     <div class="form-group">
                  <label>Student review:</label>
                     <input name="lblReview" type="text" placeholder="${ReviewLabel}" />
                     </div>
                 <p></p>
                     <input type="submit" name="btnAddReview" value="Add review" <c:if test="${students_course.size() == 0}"><c:out value="disabled='disabled'"/></c:if>/>
                     <input type="submit" name="btnGetBack" value="Get Back" onClick='location.href="/controller?command=SHOW_TEACHER_PAGE_COMMAND"' />
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