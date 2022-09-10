<%--@elvariable id="JoinFormData" type="JoinFormData"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <script src="//code.jquery.com/jquery.min.js"></script>
    <link href="/resources/css/join.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@include file="/resources/jsp/header.jsp" %>
<h2 class="join-div-above-text"> <spring:message code="login.text.title"></spring:message></h2>
<div class="join-form-div">
    <p class="join-text"><spring:message code="join.text.login"></spring:message></p>

    <form:form modelAttribute="JoinFormData" method="post" enctype="multipart/form-data" action="/join">
        <input name="username" class="input-box" placeholder="<spring:message code="join.text.id"></spring:message>" value="${username}"></input>
        <form:errors path="username" cssClass="error-text"></form:errors>
        <input name="password" type="password" class="input-box" placeholder="<spring:message code="join.text.password"></spring:message>" value="${password}"> </input>
        <form:errors path="password" cssClass="error-text"></form:errors>
        <input name="passwordCheck" type="password" class="input-box" placeholder="<spring:message code="join.text.passwordCheck"></spring:message>" value="${passwordCheck}"> </input>
        <form:errors path="passwordCheck" cssClass="error-text"></form:errors>
        <input name="nickName" class="input-box" placeholder="<spring:message code="join.text.nickname"></spring:message>" value="${nickName}"> </input>
        <form:errors path="nickName" cssClass="error-text"></form:errors>
        <div class="email-box">
            <input type="text" id="email-prefix" class="input-box-email1" onchange="setEmailHiddenBoxValue()" placeholder="<spring:message code="join.text.email"></spring:message>">
            <span id="middle">@</span>
            <input type="text" id="email-suffix" list="email-domain" class="input-box-email2" onchange="setEmailHiddenBoxValue()">
            <datalist id="email-domain">
                <option value="naver.com"></option>
                <option value="daum.com"></option>
                <option value="google.com"></option>
                <option value="직접입력"></option>
            </datalist>
            <input type="hidden" id="total-email" name="email" value="">
        </div>
        <div class="email-button-text-box">
            <button type="button" class="email-check-button" onclick="openEmailAuthPage()"> <spring:message code="join.text.emailCheckButton"></spring:message> </button>
            <p class="error-text"><form:errors path="email"></form:errors></p>
        </div>
        <p><img class="profile-image" id="profile-image" src="/resources/png/default_profile.jpg"></p>
        <p class="profile-text"> <spring:message code="join.text.profileImageText"></spring:message> </p>
        <input name="image" type="file" class="profile-image-select-box" id="chooseFile" accept="image/*" onchange="loadFile(this)">
        <textarea name="introduction" class="introduction-box" placeholder="<spring:message code="join.text.introduction"></spring:message>" value="${introduction}"></textarea>
        <button type="submit" class="join-button"> <spring:message code="join.text.joinButton"></spring:message> </button><br></br>
    </form:form>
</div>
</body>

<script>
    function loadFile(input){
        let file = input.files[0];
        let image = document.getElementById("profile-image");
        image.src = URL.createObjectURL(file);
    }
    function setEmailHiddenBoxValue(){
        let emailHiddenBox = document.getElementById("total-email");
        emailHiddenBox.value = document.getElementById("email-prefix").value + "@" + document.getElementById("email-suffix").value;
    }
    function openEmailAuthPage(){
        let email_prefix = document.getElementById("email-prefix").value;
        let email_suffix = document.getElementById("email-suffix").value;
        if(email_prefix == "" || email_suffix == "") alert("이메일을 입력해야합니다.");
        else{
            let emailHiddenBox = document.getElementById("total-email");
            let emailAddress = emailHiddenBox.value;
            window.open('/emailCertificationPage?email=' + emailAddress , '이메일 인증', 'width=450, height=300');
        }
    }
</script>
</html>