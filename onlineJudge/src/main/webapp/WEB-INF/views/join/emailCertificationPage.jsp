<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <script src="//code.jquery.com/jquery.min.js"></script>
    <link href="/resources/css/join.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <h1 class="input-box"><spring:message code="text.emailPage"> </spring:message> </h1>
    <h4 class="input-box"> ${email} <spring:message code="text.emailCertification"> </spring:message>  </h4>
    <input class="input-box" id="email-code" placeholder="<spring:message code="input.text.emailCode"> </spring:message> "> </input>
    <div class="email-check-button-div">
        <button class="email-check-button" onclick="emailCheckDialog()"> <spring:message code="button.text.sendEmail"> </spring:message> </button>
        <button class="email-check-button" onclick="checkEmailCode()"> <spring:message code="button.text.certificationComplete"> </spring:message>  </button>
    </div>
</body>

<script>
    let email = "${email}";

    function ajaxForm(type, url, data, header){
        $.ajax({
            type : type,
            url : url,
            data : data,
            headers : header,
            async: false,
            success : function(res){
                alert(res.message);
            },
            error : function(XMLHttpRequest, textStatus, errorThrown){
                alert(XMLHttpRequest.responseJSON.message);
            }
        });
    }

    function emailCheckDialog(){
        ajaxForm("POST", "/email", { email : email }, {});
    }

    function checkEmailCode(){
        let codeInput = document.getElementById("email-code");
        let code = codeInput.value;
        let result = ajaxForm("POST", "/emailCertification",{"email": email, "code" : code},{});
        alert(result.message);
        console.log(result);
        if(result.result == true) window.close();
    }
</script>
</html>