<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="/resources/css/boardListPage.css" rel="stylesheet" type="text/css"/>
    <link href="/resources/css/boardListPageSideBar.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <%@include file="/resources/jsp/header.jsp" %>

    <div class="board-root-div">
        <div id="page-wrapper">
            <div id="sidebar-wrapper">
                <ul class="sidebar-nav" id="sidebar-nav">
                </ul>
            </div>
        </div>

        <div class="board-side-div">
            <div class="board-side-list-div">
                <form method="GET" action="boardList">
                    <div class="board-side-element-search-div">
                        <input name="keyword" id="keyword" class="board-search-bar" placeholder="질문을 검색하세요!"></input>
                        <button type="submit" class="board-search-button"> 검색 </button>
                        <button class="board-search-button"> <a href="/board-write" class="board-search-bar"> 질문작성 </a> </button>
                    </div>
                    <div class="board-side-element-search-div">
                        <select name="type">
                            <option value="content">본문으로 검색</option>
                            <option value="title">제목으로 검색</option>
                        </select><br>
                    </div>
                </form>

                <div class="board-side-element-search-option-div">

                </div>

                <c:forEach var="board" items="${boards}" varStatus="idx">
                    <a class="a-tag" href="/boards?question_id=${board.board_id}">
                    <div class="board-side-element-div">
                        <div class="board-side-element-content-side">
                            <div class="board-number"> ${board.board_id}번 게시글 </div>
                            <div class="board-title"> ${board.title} </div>
                            <div class="board-content"> ${board.content} </div>
                            <div class="board-bottom">
                                <p class="board-writer-text"> • ${board.problem_id}번</p>
                                <p class="board-time-text"> • ${board.nickName}</p>
                                <p class="board-time-text"> • ${board.date}</p>
                            </div>
                        </div>

                        <div class="board-side-element-info-side">
                            <div class="board-answer-text"> • 답변수 ${board.answerCount}</div>
                            <div class="board-like-text"> • 추천수 ${board.likeCount} </div>
                        </div>
                    </div>
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
    <br>
    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <li class="page-item disabled">
                <a class="page-link" href="/boardList?page=${paging.nowPage-1}&type=${type}&keyword=${keyword}">이전 </a>
            </li>
            <c:forEach begin="${paging.leftMostPage}" end="${paging.rightMostPage}" varStatus="page">
                <li class="page-item"><a class="page-link" href="/boardList?page=${page.current}&type=${type}&keyword=${keyword}">${page.current}</a></li>
            </c:forEach>
            <li class="page-item">
                <a class="page-link" href="/boardList?page=${paging.nowPage+1}&type=${type}&keyword=${keyword}">다음</a>
            </li>
        </ul>
    </nav>
</body>
</html>
