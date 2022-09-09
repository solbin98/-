<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>'

    <link href="/resources/css/submissionListPage.css" rel="stylesheet" type="text/css" />
    <link href="/resources/css/login.css" rel="stylesheet" type="text/css" />
    <link href="/resources/css/join.css" rel="stylesheet" type="text/css" />
    <link href="/resources/css/problemListPage.css" rel="stylesheet" type="text/css" />
    <link href="/resources/css/problemPage.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@include file="/resources/jsp/header.jsp" %>
<div class="problem-list-table">
    <table class="table">
        <thead class="thead-dark">
        <th scope="col">제출번호</th>
        <th scope="col">닉네임</th>
        <th scope="col">문제번호</th>
        <th scope="col">문제이름</th>
        <th scope="col">상태</th>
        <th scope="col">수행시간</th>
        <th scope="col">메모리</th>
        <th scope="col">코드길이</th>
        <th scope="col">제출언어</th>
        <th scope="col">제출시간</th>
        </thead>

        <c:forEach var="submission" items="${submissions}" varStatus="idx">
            <tr>
                <td> ${submission.submission_id} </td>
                <td> ${submission.nickName} </td>
                <td> ${submission.problem_id}</td>
                <td><a href="/problems/${submission.problem_id}">${submission.problemName}</a></td>
                <td class="proceeding" id="submission-state${submission.submission_id}">
                        <c:if test="${submission.state eq 'CP'}">
                            채점 대기중....
                        </c:if>
                        <c:if test="${submission.state ne 'CP'}">
                            ${submission.state}
                        </c:if>
                </td>
                <td id="submission-time${submission.submission_id}"> ${submission.time} </td>
                <td id="submission-memory${submission.submission_id}"> ${submission.memory} </td>
                <td> ${submission.code_length} B </td>
                <td> ${submission.languageName} </td>
                <td> ${submission.date} </td>
                <td id="submission-testcase${submission.submission_id}" hidden> ${submission.testcase_num}</td>
            </tr>
        </c:forEach>
    </table>

    <div class="page-number-div-outer">
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <li class="page-item">
                    <a class="page-link" href="/submissionList?page=${paging.nowPage -1}">이전 </a>
                </li>
                <c:forEach begin="${paging.leftMostPage}" end="${paging.rightMostPage}" varStatus="page">
                    <li class="page-item"><a class="page-link" href="/submissionList?page=${page.current}">${page.current}</a></li>
                </c:forEach>
                <li class="page-item">
                    <a class="page-link" href="/submissionList?page=${paging.nowPage+1}">다음</a>
                </li>
            </ul>
        </nav>
    </div>

</div>
</body>
<script>
    let webSocket = new WebSocket("ws://13.209.144.139:9001/");

    webSocket.onopen = function(message){ wsOpen(message);};
    webSocket.onmessage = function(message){ wsGetMessage(message);};
    webSocket.onclose = function(message){ wsClose(message);};
    webSocket.onerror = function(message){ wsError(message);};

    function wsOpen(message){}
    function wsSendMessage(msg){}
    function getStateCodeString(state){
        if(state == -4) return "메모리 초과";
        else if(state == -3) return "시간 초과";
        else if(state == -2) return "런타임 에러";
        else if(state == -1) return "컴파일 에러";
        else if(state == 0) return "틀렸습니다!";
        else if(state == 1) return "맞았습니다!";
        else if(state == 2) return "진행중..";
    }

    function findSubmissionBySidAndUpdate(state, sid, ipn, rt, mu, endOfFile){
        let stateElement = document.getElementById("submission-state"+sid);
        let testcaseNumElement = document.getElementById("submission-testcase" + sid);
        if(testcaseNumElement == null || stateElement == null) return;

        let stateMessage = getStateCodeString(state);
        if(!endOfFile){
            let percent = (100 * ipn / parseFloat(testcaseNumElement.textContent));
            percent = Math.floor(percent);
            stateMessage = "채점중 (" + percent + "%)";
        }
        else {
            if(state < 0) stateElement.className = "wrong-answer";
            else stateElement.className = "answer-correct";

            let runningTime = document.getElementById("submission-time"+sid);
            let memoryUsage = document.getElementById("submission-memory" + sid);
            if(runningTime == null || memoryUsage == null) return;
            runningTime.textContent = rt + " ms";
            memoryUsage.textContent = mu + " kb";
        }

        stateElement.textContent = stateMessage;
    }

    function wsCloseConnection(){
        webSocket.close();
    }

    function wsGetMessage(msg){
        let message = msg.data;
        let data = message.split(":");
        let stateCode = data[0];
        let submissionNumber = data[1];
        let inputFileNumber = data[2];
        let runningTime = data[3];
        let memoryUsage = data[4];
        let endFlag = (runningTime != null && memoryUsage != null);
        findSubmissionBySidAndUpdate(stateCode, submissionNumber, inputFileNumber, runningTime, memoryUsage, endFlag);
    }

    function wsClose(message){
        echoText.value += "Disconnect ... \n";
        console.log("disconnect", message);
    }

    function wsError(message){
        echoText.value += "Error \n";
        console.log("error", message);
    }

</script>
</html>


