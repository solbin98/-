<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <form method="post" enctype="multipart/form-data" action="/join">
            <input name="id" class="input-box" placeholder="<spring:message code="join.text.id"></spring:message>" required> </input>
            <input name="password" type="password" class="input-box" placeholder="<spring:message code="join.text.password"></spring:message>" required> </input>
            <input name="passwordCheck" type="password" class="input-box" placeholder="<spring:message code="join.text.passwordCheck"></spring:message>" required> </input>
            <input name="nickname" class="input-box" placeholder="<spring:message code="join.text.nickname"></spring:message>" required> </input>
            <div class="email-box">
                <input type="text" id="email-prefix" class="input-box-email1" placeholder="<spring:message code="join.text.email"></spring:message>">
                <span id="middle">@</span>
                <input type="text" id="email-suffix" list="email-domain" class="input-box-email2">
                <datalist id="email-domain">
                    <option value="naver.com"></option>
                    <option value="daum.com"></option>
                    <option value="google.com"></option>
                    <option value="직접입력"></option>
                </datalist>
                <input type="hidden" id="total-email" name="email" value="">
            </div>
            <button type="button" class="email-check-button" onclick="openEmailAuthPage()"> <spring:message code="join.text.emailCheckButton"></spring:message> </button>
            <p><img class="profile-image" id="profile-image" src="/resources/png/default_profile.jpg"></p>
            <p class="profile-text"> <spring:message code="join.text.profileImageText"></spring:message> </p>
            <input name="image" type="file" class="profile-image-select-box" id="chooseFile" name="chooseFile" accept="image/*" onchange="loadFile(this)">
            <textarea name="introduction" class="introduction-box" placeholder="<spring:message code="join.text.introduction"></spring:message>"></textarea>
            <button type="submit" class="join-button"> <spring:message code="join.text.joinButton"></spring:message> </button><br></br>
        </form>
    </div>
</body>

<script>
    function loadFile(input){
        let file = input.files[0];
        let image = document.getElementById("profile-image");
        image.src = URL.createObjectURL(file);
    }


    function openEmailAuthPage(){
        let email_prefix = document.getElementById("email-prefix").value;
        let email_suffix = document.getElementById("email-suffix").value;
        if(email_prefix == "" || email_suffix == ""){
            alert("이메일을 입력해야합니다.");
        }
        else{
            let emailAddress = email_prefix + "@" + email_suffix;
            window.open('/emailCertificationPage?email=' + emailAddress , '이메일 인증', 'width=450, height=300');
        }
    }

</script>

</html>
