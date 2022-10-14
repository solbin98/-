<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
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
        <input id="member_id${question.board_id}" value="${question.member_id}" hidden></input>
        <div class="question-title">
            <h3> Q. ${question.title}</h3>
            <a href="/problem/${question.problem_id}"><h4> ${question.problem_id}번 문제</h4></a>
        </div>
        <div class="board-headline">
            <div class="board-headline-middle-div">
                <p class="board-headline-nickName"> ${question.nickName} </p>
                <p class="board-headline-date">  ${question.date} </p>
            </div>
            <sec:authorize access="isAuthenticated()">
                <div class="board-headline-back-div">
                    <sec:authentication property="principal" var="user"/>
                    <c:if test="${user.nickname eq question.nickName}">
                        <p class="board-edit-text"><a href="/boards/question-update?board_id=${question.board_id}">• <spring:message code="text.edit"> </spring:message> </a></p>
                        <p onclick="deleteBoard(${question.board_id}, ${question.board_id},${question.member_id})" class="board-edit-text"> • <spring:message code="text.remove"> </spring:message>  </p>
                    </c:if>
                </div>
                <div class="board-headline-last-div">
                    <form method="post" action="/like">
                        <input value="${question.board_id}" name="board_id" hidden>
                        <button class="board-edit-text" style="background: none; border: none" type="submit">• <spring:message code="text.like"> </spring:message> (${question.likeCount})</button>
                        <p class="board-edit-text"></p>
                    </form>
                </div>
            </sec:authorize>
        </div>
        <div class="board-content">${question.content}</div>
    </div>

    <!-- 답변 게시글 불러오기 -->
    <c:forEach var="answer" items="${answers}" varStatus="idx">
        <div class="board-block-full" id="board${answer.board_id}">
                <div class="board-headline">
                    <div class="board-headline-middle-div">
                        <p class="board-headline-nickName"> ${answer.nickName} </p>
                        <p class="board-headline-date"> ${answer.date} </p>
                    </div>
                    <div class="board-headline-back-div">
                        <sec:authorize access="isAuthenticated()">
                            <sec:authentication property="principal" var="user"/>
                            <c:if test="${user.nickname eq answer.nickName}">
                                <p class="board-edit-text" onclick="updateAnswer(${question.board_id},${answer.board_id},${answer.member_id})"> • <spring:message code="text.edit"> </spring:message>  <p/>
                                <p></p>
                                <p onclick="deleteBoard(${question.board_id}, ${answer.board_id}, ${answer.member_id})" class="board-edit-text"> • <spring:message code="text.remove"> </spring:message>  </p>
                            </c:if>
                        </sec:authorize>
                    </div>
                </div>
                <div id="content${answer.board_id}" class="board-content">${answer.content}</div>
        </div>
    </c:forEach>

    <div class="board-block-full">
        <sec:authorize access="isAuthenticated()">
            <h3 id="submit-title" class="tag-text"><spring:message code="text.answerDo"> </spring:message> </h3>
            <div class="editor" id="editor"></div>
            <div class="submit-button-div">
                <button onclick="submitAnswer(${question.board_id})" class="submit-button" id="submit-button"> <spring:message code="button.text.submit"></spring:message> </button>
            </div>
        </sec:authorize>
        <!-- "/login?redirect="${request.getContextPath()} -->
        <sec:authorize access="isAnonymous()">
            <h3 class="tag-text">답변을 작성하려면 <a href="/login"> 로그인 </a> 해야합니다.</h3>
        </sec:authorize>
    </div>
</body>

<script>
    <%@include file="/resources/js/board.js" %>
</script>
</html>
