<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html>
<head>
    <link href="/resources/css/header.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<div class="header-div">
    <div class="header-inner-div">
        <img class="logo-image" src="/resources/png/logo.jpg">
        <h3 class="logo-text">  <spring:message code="menu.text.title"> </spring:message> </h3>
        <div class="header-text-div">
            <a href="/problems" methods="GET" class="header-text" id="problem-link"> <spring:message code="menu.text.problem"> </spring:message> </a>
            <a href="/boards" methods="GET" class="header-text" id="board-link">  <spring:message code="menu.text.board"> </spring:message> </a>
            <a href="/boards" methods="GET" class="header-text" id="submission-link">  <spring:message code="menu.text.submission"> </spring:message> </a>
            <a href="/ranking" methods="GET" class="header-text" id="ranking-link">  <spring:message code="menu.text.ranking"> </spring:message> </a>
            <a href="/profile" methods="GET" class="header-text" id="write-board">   <spring:message code="menu.text.profile"> </spring:message> </a>
        </div>

        <div class="header-text-div">
            <a href="/login" methods="GET" class="header-text" id="login-link">  <spring:message code="menu.text.login"> </spring:message> </a>
            <a href="/join" methods="GET" class="header-text" id="join-link">   <spring:message code="menu.text.join"> </spring:message> </a>
        </div>
    </div>
</div>
</body>
</html>