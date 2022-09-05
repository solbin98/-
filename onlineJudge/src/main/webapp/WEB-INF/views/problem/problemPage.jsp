<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link href="/resources/css/problemPage.css" rel="stylesheet" type="text/css" />
    <!-- katex 추가하는 부분 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/katex@0.11.1/dist/katex.min.css" integrity="sha384-zB1R0rpPzHqg7Kpt0Aljp8JPLqbXI3bhnPWROx27a9N0Ll6ZP/+DiW/UqRcLbRjq" crossorigin="anonymous"/>
    <script defer src="https://cdn.jsdelivr.net/npm/katex@0.11.1/dist/katex.min.js" integrity="sha384-y23I5Q6l+B6vatafAwxRu/0oK/79VlbSz7Q9aiSZUvyWYIYsd+qj+o24G5ZU2zJz" crossorigin="anonymous"></script>
    <script defer src="https://cdn.jsdelivr.net/npm/katex@0.11.1/dist/contrib/auto-render.min.js" integrity="sha384-kWPLUVMOks5AQFrykwIup5lo0m3iMkkHrD0uJ4H5cjeGihAutqP0yW0J6dpFiVkI" crossorigin="anonymous" onload="renderMathInElement(document.body);"></script>
    <script>
        let options = {
            delimiters: [
                { left: "$$", right: "$$", display: true },
                { left: "$", right: "$", display: false },
            ],
            throwOnError : false
        }
        document.addEventListener("DOMContentLoaded", function () {
            renderMathInElement(document.body, options);
        });
    </script>
    <!-- katex 끝 -->
</head>
<body>
    <%@include file="/resources/jsp/header.jsp" %>

    <div class="content-side">
        <div class="board-block-full">
            <div class="board-headline" id="board-headline">
                <h2 class="board-headline-title"> ${problem.title} </h2>
                <img src="/resources/png/delete.png" class="board-delete" id="board-delete">
                <a href="/board-update-page/${problem.problem_id}" class="board-edit-outer" id="board-edit"> <img src="/resources/png/edit.png" class="board-edit" > </img> </a>
            </div>

            <div class="board-tagline" id = "board-tagline">
                <div class="tag-front"> • <spring:message code="text.timeLimit"></spring:message> : </div>
                <div class="tag-box"> ${problem.time_limit}</div>
                <div class="tag-front"> • <spring:message code="text.memoryLimit"></spring:message> : </div>
                <div class="tag-box"> ${problem.memory_limit}</div>
                <div class="tag-front"> • <spring:message code="text.submission"></spring:message> : </div>
                <div class="tag-box"> ${submitNumber}</div>
                <div class="tag-front"> • <spring:message code="text.acSubmission"></spring:message> : </div>
                <div class="tag-box"> ${acSubmitNumber}</div>
                <div class="tag-front"> • <spring:message code="text.difficulty"></spring:message> : </div>
                <div class="tag-box"> ${problem.difficulty}</div>
            </div>

            <div class="board-content">${problem.content}</div>

            <div class="board-tagline">
                <div class="tag-front"> • <spring:message code="text.tag"></spring:message> : </div>
                <c:forEach var="tag" items="${tags}">
                    <div class="tag-box">${tag.name}</div>
                </c:forEach>
            </div>

        </div>
    </div>

    <div class="content-side">
        <div class="board-block-full">
            <div class="testcase-text-header">
                <h2 class="testcase-text">  조건 제한 </h2>
            </div>

            <h5 class="testcase-text">  <spring:message code="text.inputLimit"></spring:message> </h5>
            <p class="testcase-text-box"> ${problem.input_condition}</p>

            <h5 class="testcase-text">  <spring:message code="text.outputLimit"></spring:message> </h5>
            <p class="testcase-text-box"> ${problem.output_condition}</p>
        </div>
    </div>


    <div class="content-side">
        <div class="board-block-full">
            <div class="testcase-text-header">
                <h2 class="testcase-text">  <spring:message code="text.testcase"></spring:message> </h2>
            </div>
            <c:forEach var="testcase" items="${testcases}" varStatus="idx">
                    <h5 class="testcase-text">  <spring:message code="text.input"></spring:message> ${idx.index + 1} </h5>
                    <p class="testcase-text-box"> ${testcase.input}</p>

                    <h5 class="testcase-text">  <spring:message code="text.output"></spring:message> ${idx.index + 1}</h5>
                    <p class="testcase-text-box"> ${testcase.output}</p>
                    <br></br>
                    <p class="testcase-bottom-line"></p>
            </c:forEach>
        </div>
    </div>
    <a href="/submissionPage?problem_id=${problem.problem_id}"><button class="submit-button" onclick=""> 제출 하기</button> </a>
</body>
</html>
