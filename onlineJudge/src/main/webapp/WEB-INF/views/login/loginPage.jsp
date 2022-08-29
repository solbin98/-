<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="/resources/css/login.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <%@include file="/resources/jsp/header.jsp" %>

    <h2 class="login-div-above-text"> <spring:message code="login.text.title"></spring:message></h2>
    <div class="login-div">
        <p class="login-text"><spring:message code="login.text.login"></spring:message></p>
        <input class="id-box" placeholder=<spring:message code="login.text.id"></spring:message>> </input>
        <input class="password-box" placeholder=<spring:message code="login.text.password"></spring:message>> </input>
        <button class="login-button"> <spring:message code="login.text.loginButton"></spring:message> </button><br></br>
        <a href="/join" class="join-text"> <spring:message code="login.text.joinText"></spring:message> </a>
    </div>
</body>
</html>
