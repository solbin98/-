<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="/resources/css/profilePage.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@include file="/resources/jsp/header.jsp" %>

<div class="profile-root-div">
    <h2 class="title"> <spring:message code="text.myProfile"> </spring:message>  </h2>
    <div class="introduction-box">
        <sec:authentication property="principal" var="user"/>
        <h6> <spring:message code="text.nickname"> </spring:message>  : ${user.nickname}</h6>
        <h6 class="submit-text"> <spring:message code="text.id"> </spring:message> : ${user.username}</h6>
    </div>
    <br><br>
    <div class="introduction-box">
        <form class="introduction-box" method="post" action="/introduction">
            <textarea class="content-80" id="introduction" name="introduction">${introduction}</textarea>
            <button type="submit" class="content-15"> <spring:message code="button.text.editIntro"> </spring:message>  </button>
        </form>

    </div>
</div>

<div class="profile-root-div">
    <h2 class="title"> <spring:message code="text.history"> </spring:message>  </h2>
    <div class="introduction-box">
        <h4> <spring:message code="text.triedProblem"> </spring:message>  : ${profileDto.submission_num}</h4>
        <h4 class="submit-text"> <spring:message code="text.solvedProblem"> </spring:message>  : ${profileDto.solved_num}</h4>
        <h4 class="submit-text"> <spring:message code="text.acRate"> </spring:message>  : ${ 100 * (profileDto.solved_num) / profileDto.submission_num} </h4>
    </div>
    <br>

    <div class="solved-problem-div">
        <c:forEach var="problem" items="${problemIdList}"  varStatus="idx">
            <a href="/problem/${problem.problem_id}" class="solved-problem-text"><h5>${problem.problem_id}번 - ${problem.problem_name}</h5></a>
        </c:forEach>
    </div>
</div>

</body>
</html>
