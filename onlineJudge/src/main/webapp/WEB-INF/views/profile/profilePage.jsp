<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="/resources/css/profilePage.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@include file="/resources/jsp/header.jsp" %>

<div class="profile-root-div">
    <h2 class="title"> 내 프로필 </h2>
    <div class="introduction-box">
        <sec:authentication property="principal" var="user"/>
        <h6> 닉네임 : ${user.nickname}</h6>
        <h6 class="submit-text"> 아이디: ${user.username}</h6>
    </div>
    <br><br>
    <div class="introduction-box">
        <form class="introduction-box" method="post" action="/introduction">
            <textarea class="content-80" id="introduction" name="introduction">${introduction}</textarea>
            <button type="submit" class="content-15"> 자기소개 수정하기 </button>
        </form>

    </div>
</div>

<div class="profile-root-div">
    <h2 class="title"> 히스토리 </h2>
    <div class="introduction-box">
        <h4> 시도한 문제 : ${profileDto.submission_num}</h4>
        <h4 class="submit-text"> 맞은 문제 : ${profileDto.solved_num}</h4>
        <h4 class="submit-text"> 정답률(%) : ${ 100 * (profileDto.solved_num) / profileDto.submission_num} </h4>
    </div>
    <br>

    <div class="solved-problem-div">
        <c:forEach var="problem" items="${problemIdList}"  varStatus="idx">
            <a href="/problems/${problem.problem_id}" class="solved-problem-text"><h5>${problem.problem_id}번 - ${problem.problem_name}</h5></h3></a>
        </c:forEach>
    </div>
</div>

</body>
</html>
