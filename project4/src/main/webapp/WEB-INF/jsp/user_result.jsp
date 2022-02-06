<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="yourResults" var="yourResults"/>
<fmt:message bundle="${loc}" key="youHaveNotAnyReviews" var="youHaveNotAnyReviews"/>
<fmt:message bundle="${loc}" key="courseName" var="courseName"/>
<fmt:message bundle="${loc}" key="Grade" var="Grade"/>
<fmt:message bundle="${loc}" key="Review" var="Review"/>
<fmt:message bundle="${loc}" key="getBack" var="getBack"/>
<fmt:message bundle="${loc}" key="next" var="next"/>
<fmt:message bundle="${loc}" key="previous" var="previous"/>

<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <%@ include file="header/header.jsp" %>
    <style>
        <%@include file="/WEB-INF/css/labels_buttons.css"%><%@include file="/WEB-INF/css/tables.css"%>
    </style>
</head>

<body>
    <h3>${yourResults}</h3>
    <c:choose>
        <c:when test="${user_review.size() eq 0}">
            <div class="exception">
                <p>${youHaveNotAnyReviews}</p>
                <div>
        </c:when>
        <c:otherwise>
            <table border="1" table style="width:33%" style="text-align:center">
                <c:forEach items="${requestScope.user_review}" var="review">
                    <thead>
                        <tr>
                            <th>${courseName}</th>
                            <th>${Grade}</th>
                            <th>${Review}</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                <c:out value="${review.courseName}" />
                            </td>
                            <td>
                                <c:out value="${review.grade}" />
                            </td>
                            <td>
                                <c:out value="${review.review}" />
                            </td>
                        </tr>
                    </tbody>
                </c:forEach>
            </table>
        <div class="paggination">
        <c:if test="${current_page != 1}">
            <td>
                <a
                    href="/controller?command=SHOW_REVIEW_PAGE_COMMAND&page=${current_page - 1}">${previous}</a>
            </td>
        </c:if>

        <c:forEach begin="1" end="${number_of_pages}" var="i">
            <c:choose>
                <c:when test="${current_page eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td>
                        <a href="/controller?command=SHOW_REVIEW_PAGE_COMMAND&page=${i}">${i}</a>
                    </td>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <%--For displaying Next link --%>
            <c:if test="${current_page lt number_of_pages}">
                <td>
                    <a
                        href="/controller?command=SHOW_REVIEW_PAGE_COMMAND&page=${current_page + 1}">${next}</a>
                </td>
            </c:if>
            </div>
        </c:otherwise>
    </c:choose>
    <p></p>
    <form action="/controller?command=SHOW_USER_PAGE_COMMAND" method="post">
        <button type="submit" name="btnGetBack">${getBack}</button>
    </form>
    <p></p>
    <%@ include file="footer/footer.jsp" %>
</body>
</html>