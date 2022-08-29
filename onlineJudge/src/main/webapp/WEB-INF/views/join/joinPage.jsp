<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="/resources/css/join.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <%@include file="/resources/jsp/header.jsp" %>
    <h2 class="join-div-above-text"> <spring:message code="login.text.title"></spring:message></h2>
    <div class="join-form-div">
        <p class="join-text"><spring:message code="join.text.login"></spring:message></p>
        <input class="input-box" placeholder=<spring:message code="join.text.id"></spring:message>> </input>
        <input class="input-box" placeholder=<spring:message code="join.text.password"></spring:message>> </input>
        <input class="input-box" placeholder=<spring:message code="join.text.passwordCheck"></spring:message>> </input>
        <input class="input-box" placeholder=<spring:message code="join.text.nickname"></spring:message>> </input>
        <textarea class="introduction-box" placeholder=<spring:message code="join.text.introduction"></spring:message>> </textarea>
        <button class="join-button"> <spring:message code="join.text.joinButton"></spring:message> </button><br></br>
    </div>
</body>
</html>
