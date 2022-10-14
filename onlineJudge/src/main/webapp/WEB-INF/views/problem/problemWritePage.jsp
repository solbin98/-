<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="/resources/css/problemWritePage.css">
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
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
            //renderMathInElement(document.body, options);
        });
    </script>
    <!-- katex 끝 -->
</head>
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script src="//code.jquery.com/jquery.min.js"></script>
<body>
    <h2 class="title-text"> <spring:message code="text.title"></spring:message> </h2>
    <input class="title" type="text" placeholder="<spring:message code="input.text.title"></spring:message>" id="title" value="${title}">
    <div class="editor" id="editor"></div>

    <div class="tag-box">
        <h3 class="tag-text"> <spring:message code="text.tag"></spring:message> </h3>
        <input class="tag-input" type="text" placeholder="<spring:message code="input.text.tag"></spring:message>" id="tag-text" value="${tags}">
    </div>

    <div class="tag-box">
        <h3 class="tag-text"> <spring:message code="text.timeLimit"></spring:message> </h3>
        <input class="limit-text-input" type="text" placeholder="<spring:message code="input.text.timeLimit"></spring:message>" id="time-limit-text">
    </div>

    <div class="tag-box">
        <h3 class="tag-text"> <spring:message code="text.memoryLimit"></spring:message> </h3>
        <input  class="limit-text-input" type="text" placeholder="<spring:message code="input.text.memoryLimit"></spring:message>" id="memory-limit-text">
    </div>

    <div class="tag-box">
        <h3 class="tag-text"> <spring:message code="text.inputLimit"></spring:message> </h3>
        <input class="tag-input" type="text" placeholder="<spring:message code="input.text.inputLimit"></spring:message>" id="input-limit-text">
    </div>

    <div class="tag-box">
        <h3 class="tag-text"> <spring:message code="text.outputLimit"></spring:message> </h3>
        <input class="tag-input" type="text" placeholder="<spring:message code="input.text.outputLimit"></spring:message>" id="output-limit-text">
    </div>

    <div class="testcase-box">
        <h3 class="tag-text"> <spring:message code="text.testcase"></spring:message> </h3>
        <button class="testcase-add-button" onclick="createTestCaseElements()" class="submit-button"> 케이스 추가 </button>
        <p></p>
        <div id="testcase-div"><!-- 동적으로 테스트 케이스 박스들이 추가되는 곳 --></div>
    </div>

    <div class="tag-box">
        <h3 class="tag-text"> <spring:message code="text.difficulty"></spring:message> </h3>
        <input  class="limit-text-input" type="text" placeholder="<spring:message code="input.text.difficulty"></spring:message>" id="difficulty">
    </div>

    <div class="testcase-box">
        <h3>Input <spring:message code="text.add"> </spring:message> </h3>
        <input name="input" type="file" class="profile-image-select-box" id="inputFile" multiple  accept="multipart/form-data" onchange="loadFile(this)">
        <h3>Output <spring:message code="text.add"> </spring:message> </h3>
        <input name="output" type="file" class="profile-image-select-box" id="outputFile" multiple  accept="multipart/form-data" onchange="loadFile(this)">
    </div>

    <div class="submit-button-div">
        <button onclick="submitProblem()" class="submit-button" id="submit-button"> <spring:message code="button.text.submit"></spring:message> </button>
    </div>

    <div class="submit-button-div">
        <button onclick="submitTest()" class="submit-button"> <spring:message code="button.text.preview"> </spring:message>  </button>
    </div>

    <p id="preview-Page"></p>
</body>
<script>
    <%@include file="/resources/js/board.js" %>
</script>
</html>
