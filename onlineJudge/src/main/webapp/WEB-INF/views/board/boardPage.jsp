<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="/resources/css/boardPage.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
    <script src="//code.jquery.com/jquery.min.js"></script>
    <script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
</head>
<body>
    <%@include file="/resources/jsp/header.jsp" %>

    <!-- 질문 게시글 -->
    <div class="board-block-full" id="board1">
        <div class="question-title">
            <h3> Q. ${question.title}</h3>
            <a href="/problems/${question.problem_id}"><h4> ${question.problem_id}번 문제</h4></a>
        </div>
        <div class="board-headline">
            <div calss="board-headline-front-div">
                <p class="board-headline-image"> <img class="board-headline-image" src="/resources/png/default_profile.jpg"> </p>
            </div>
            <div class="board-headline-middle-div">
                <p class="board-headline-nickName"> ${question.nickName} </p>
                <p class="board-headline-date"> ${question.date} </p>
            </div>
            <div class="board-headline-back-div">
                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="principal" var="user"/>
                    <c:if test="${user.nickname eq question.nickName}">
                        <p class="board-edit-text">
                            <a href="/boards/question-update?content=${question.content}&title=${question.title}&problem_id=${question.problem_id}&board_id=${question.board_id}">• 수정하기</a>
                        </p>
                        <p></p>
                        <p class="board-edit-text"> • 삭제하기 </p>
                    </c:if>
                </sec:authorize>
            </div>
        </div>
        <div class="board-content">${question.content}</div>
    </div>

    <!-- 답변 게시글 불러오기 -->
    <c:forEach var="answer" items="${answers}" varStatus="idx">
        <div class="board-block-full" id="board${idx.index + 2}">
                <div class="board-headline">
                    <div calss="board-headline-front-div">
                        <p class="board-headline-image"> <img class="board-headline-image" src="/resources/png/default_profile.jpg"> </p>
                    </div>
                    <div class="board-headline-middle-div">
                        <p class="board-headline-nickName"> ${answer.nickName} </p>
                        <p class="board-headline-date"> ${answer.date} </p>
                    </div>
                    <div class="board-headline-back-div">
                        <sec:authorize access="isAuthenticated()">
                            <sec:authentication property="principal" var="user"/>
                            <c:if test="${user.nickname eq answer.nickName}">
                                <p class="board-edit-text"> • 수정하기 </p>
                                <p></p>
                                <p class="board-edit-text"> • 삭제하기 </p>
                            </c:if>
                        </sec:authorize>
                    </div>
                </div>
                <div class="board-content">${answer.content}</div>
        </div>
    </c:forEach>

    <div class="board-block-full">
        <sec:authorize access="isAuthenticated()">
            <h3 class="tag-text">답변 작성</h3>
            <div class="editor" id="editor"></div>
            <div class="submit-button-div">
                <button onclick="submitAnswer(${question.board_id})" class="submit-button" id="submit-button"> <spring:message code="button.text.submit"></spring:message> </button>
            </div>
        </sec:authorize>

        <sec:authorize access="isAnonymous()">
            <h3 class="tag-text">답변을 작성하려면 <a href="/login"> 로그인 </a> 해야합니다.</h3>
        </sec:authorize>
    </div>
</body>

<script>
    <%@include file="/resources/js/writeBoard.js" %>
</script>
</html>
