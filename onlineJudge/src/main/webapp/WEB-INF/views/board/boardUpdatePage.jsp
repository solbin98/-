<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link href="/resources/css/boardWritePage.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
    <script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
    <script src="//code.jquery.com/jquery.min.js"></script>
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
<body>
<%@include file="/resources/jsp/header.jsp" %>
<div class="board-write-root-div">
    <input id="member_id" value="${member_id}" hidden></input>
    <div class="tag-box"> <h3 class="tag-text">질문 수정</h3>  </div>
    <div class="title-problem-div">
        <input class="problem" type="text" readonly placeholder="문제번호" id="problem_id" value="${problem_id}">
        <input class="title" type="text" placeholder="<spring:message code="input.text.title"></spring:message>" id="title" value="${title}">
    </div>

    <div class="editor" id="editor"></div>

    <div class="submit-button-div">
        <button onclick="updateQuestion(${board_id},${member_id})" class="submit-button" id="submit-button"> <spring:message code="button.text.submit"></spring:message> </button>
    </div>

    <div class="line"></div>

    <div class="submit-button-div">
        <button onclick="submitTest()" class="submit-button"> 게시글 미리보기 </button>
    </div>

    <p id="preview-Page"></p>
</div>
</body>
<script>
    <%@include file="/resources/js/writeBoard.js" %>
    editor.setHTML("${content}");
</script>
</html>
