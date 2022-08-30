<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <script src="//code.jquery.com/jquery.min.js"></script>
    <link href="/resources/css/join.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <h1 class="input-box">이메일 인증 페이지</h1>
    <h4 class="input-box"> ${email} 계정의 소유를 인증합니다. </h4>
    <input class="input-box" id="email-code" placeholder="인증 코드를 입력해주세요."> </input>
    <div class="email-check-button-div">
        <button class="email-check-button" onclick="emailCheckDialog()"> 인증요청 </button>
        <button class="email-check-button" onclick="checkEmailCode()"> 인증완료 </button>
    </div>
</body>

<script>
    let email = "${email}";

    function ajaxForm(type, url, data, header){
        let result;
        $.ajax({
            type : type,
            url : url,
            data : data,
            headers : header,
            async: false,
            success : function(res){
                result = res;
            },
            error : function(XMLHttpRequest, textStatus, errorThrown){
                alert("통신 에러");
            }
        });
        return result;
    }

    function emailCheckDialog(){
        let message = ajaxForm("POST", "/email", { email : email }, {});
        let result = alert(message);
        console.log(result);
    }


    function checkEmailCode(){
        let codeInput = document.getElementById("email-code");
        let code = codeInput.value;
        let result = ajaxForm("POST", "/emailCertification",{"email": email, "code" : code},{});
        alert(result.message);
        console.log(result);
        //if(result.result == true) window.close();
    }
</script>
</html>