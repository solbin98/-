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
    <div class="p-5 bg-image" style="background-image: url('/resources/png/login-background.jpg');height: 200%;"></div>
    <div class="join-form-div">
        <section class="text-center">
            <div class="card mx-4 mx-md-5 shadow-5-strong" style="margin-top: -197.5%;background: hsla(0, 0%, 100%, 0.8);backdrop-filter: blur(30px); ">
                <div class="card-body py-5 px-md-5">
                    <div class="row d-flex justify-content-center">
                        <div class="col-lg-8">
                            <h2 class="fw-bold mb-5"> <spring:message code="text.websiteTitle"></spring:message></h2>
                            <form:form modelAttribute="JoinFormData" method="post" enctype="multipart/form-data" action="/join">
                                <div class="form-outline mb-4">
                                    <input name="username" class="form-control" placeholder="<spring:message code="text.id"></spring:message>" value="${username}"></input>
                                    <form:errors path="username" cssClass="error-text"></form:errors>
                                </div>

                                <!-- Password input -->
                                <div class="form-outline mb-4">
                                    <input name="password" type="password" class="form-control" placeholder="<spring:message code="text.password"></spring:message>" value="${password}"> </input>
                                    <form:errors path="password" cssClass="error-text"></form:errors>
                                </div>

                                <div class="form-outline mb-4">
                                    <input name="passwordCheck" type="password" class="form-control" placeholder="<spring:message code="text.passwordCheck"></spring:message>" value="${passwordCheck}"> </input>
                                    <form:errors path="passwordCheck" cssClass="error-text"></form:errors>
                                </div>

                                <div class="form-outline mb-4">
                                    <input name="nickName" class="form-control" placeholder="<spring:message code="text.nickname"></spring:message>" value="${nickName}"> </input>
                                    <form:errors path="nickName" cssClass="error-text"></form:errors>
                                </div>


                                <div class="row">
                                    <div class="col-md-6 mb-4">
                                        <div class="form-outline">
                                            <input type="text" id="email-prefix" class="form-control" onchange="setEmailHiddenBoxValue()" placeholder="<spring:message code="text.email"></spring:message>"></input>
                                        </div>
                                    </div>
                                    <div class="col-md-6 mb-4">
                                        <div class="form-outline">
                                            <input type="text" id="email-suffix" list="email-domain" class="form-control" placeholder="이메일 주소" onchange="setEmailHiddenBoxValue()">
                                            <datalist id="email-domain">
                                                <option value="naver.com"></option>
                                                <option value="daum.com"></option>
                                                <option value="google.com"></option>
                                                <option value="직접입력"></option>
                                            </datalist>
                                            <input type="hidden" id="total-email" name="email" value="">
                                        </div>
                                    </div>
                                </div>

                                <div class="email-box">
                                    <div class="email-button-text-box">
                                        <button type="button" class="form-control" onclick="openEmailAuthPage()"> <spring:message code="text.emailCheckButton"></spring:message> </button>
                                        <p class="error-text"><form:errors path="email"></form:errors></p>
                                    </div>
                                </div>
                                <div class="form-outline mb-4">
                                    <textarea class="form-control" name="introduction" class="introduction-box" placeholder="<spring:message code="input.text.introduction"></spring:message>" value="${introduction}"></textarea>
                                </div>
                                <!-- Submit button -->
                                <button type="submit" class="btn btn-primary btn-block mb-4"><spring:message code="text.joinText"></spring:message></button>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</body>

<script>
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
