<%--@elvariable id="LoginInfoData" type="LoginInfoData"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link href="/resources/css/login.css" rel="stylesheet" type="text/css" />
    <link href="/resources/css/join.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <%@include file="/resources/jsp/header.jsp" %>

    <h2 class="login-div-above-text"> <spring:message code="login.text.title"></spring:message></h2>
    <div class="login-div">
        <p class="login-text"><spring:message code="login.text.login"></spring:message></p>
        <form:form modelAttribute="LoginInfoData" method="post" action="/login">
            <input name="username" class="id-box" placeholder="<spring:message code="login.text.id"></spring:message>" value="${username}"> </input>
            <p class="error-text-small">${error}</p>
            <input name="password" class="password-box" type="password" placeholder="<spring:message code="login.text.password"></spring:message>" value="${password}"> </input>
            <br></br>
            <button class="login-button" type="submit"> <spring:message code="login.text.loginButton"></spring:message> </button><br></br>
        </form:form>
        <a href="/join" class="join-text-login-css"> <spring:message code="login.text.joinText"></spring:message> </a>
    </div>
</body>
</html>
