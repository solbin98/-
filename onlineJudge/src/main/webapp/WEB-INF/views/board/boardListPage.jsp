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
                    <h6 class="category-text"> • 질문 & 답변 </h6>
                    <h6 class="category-text"> • 자유게시판 </h6>
                    <!-- 동적으로 카테고리 추가 -->
                </ul>
            </div>
        </div>

        <div class="board-side-div">
            <div class="board-side-list-div">
                <div class="board-side-element-search-div">
                    <input class="board-search-bar" placeholder="질문을 검색하세요!"></input>
                    <button class="board-search-button"> 검색 </button>
                </div>

                <div class="board-side-element-search-option-div">
                    <p class="board-order-text"> • 최신순 </p>
                    <p class="board-order-text"> • 좋아요 순 </p>
                    <p class="board-order-text"> • 답변 많은 순서 </p>
                    <button class="board-write-button"> <a href="/board-write"> 질문작성 </a> </button>
                </div>

                <c:forEach var="board" items="${boards}" varStatus="idx">
                    <!-- 동적으로 게시글들 추가되면 될 것임. -->
                    <div class="board-side-element-div">
                        <div class="board-side-element-content-side">
                            <div class="board-title"> [${board.problem_id}] ${board.title} </div>
                            <div class="board-content"> ${board.content} </div>
                            <div class="board-bottom">
                                <p class="board-writer-text"> ${board.nickName}</p>
                                <p class="board-time-text"> ${board.date}</p>
                            </div>
                        </div>

                        <div class="board-side-element-info-side">
                            <div class="board-answer-text"> ${board.answerCount}</div>
                            <div class="board-like-text"> ${board.likeCount} </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <br></br><br></br><br></br>
    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <li class="page-item disabled">
                <a class="page-link" href="/boardList?page=${paging.nowPage-1}">이전 </a>
            </li>
            <c:forEach begin="${paging.leftMostPage}" end="${paging.rightMostPage}" varStatus="page">
                <li class="page-item"><a class="page-link" href="/boardList?page=${page.current}">${page.current}</a></li>
            </c:forEach>
            <li class="page-item">
                <a class="page-link" href="/boardList?page=${paging.nowPage+1}">다음</a>
            </li>
        </ul>
    </nav>
</body>
</html>
