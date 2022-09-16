<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- 부트 스트랩 의존 추가 시작 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
    <!-- 부트 스트랩 의존 추가 끝 -->
    <link href="/resources/css/problemListPage.css" rel="stylesheet" type="text/css" />
    <link href="/resources/css/problemPage.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@include file="/resources/jsp/header.jsp" %>
<div class="problem-list-table">
    <table class="table table-striped">
        <thead>
        <th scope="col">순위</th>
        <th scope="col">닉네임</th>
        <th scope="col">자기소개</th>
        <th scope="col">맞은 문제수</th>
        <th scope="col">시도한 문제수</th>
        </thead>

        <c:forEach var="ranking" items="${rankingData}" varStatus="idx">
            <tr>
                <td> ${(paging.nowPage-1) * 50 + (idx.index) + 1} </td>
                <td> ${ranking.nickname} </td>
                <td> ${ranking.introduction} </td>
                <td> ${ranking.acSubmissionNumber} </td>
                <td> ${ranking.submissionNumber} </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="page-number-div-outer">
    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <li class="page-item disabled"><a class="page-link" href="/rankingPage?page=${paging.nowPage-1}">이전</a></li>
            <c:forEach begin="${paging.leftMostPage}" end="${paging.rightMostPage}" varStatus="page">
                <li class="page-item"><a class="page-link" href="/rankingPage?page=${page.current}">${page.current}</a></li>
            </c:forEach>
            <li class="page-item"><a class="page-link" href="/rankingPage?page=${paging.nowPage+1}">다음</a></li>
        </ul>
    </nav>
</div>
</body>
</html>
