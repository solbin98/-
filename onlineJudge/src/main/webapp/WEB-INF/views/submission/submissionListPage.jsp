<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>'

    <link href="/resources/css/login.css" rel="stylesheet" type="text/css" />
    <link href="/resources/css/join.css" rel="stylesheet" type="text/css" />
    <link href="/resources/css/problemListPage.css" rel="stylesheet" type="text/css" />
    <link href="/resources/css/problemPage.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@include file="/resources/jsp/header.jsp" %>
<div class="problem-list-table">
    <table class="table table-striped">
        <thead>
        <th scope="col">제출번호</th>
        <th scope="col">닉네임</th>
        <th scope="col">문제번호</th>
        <th scope="col">문제이름</th>
        <th scope="col">상태</th>
        <th scope="col">메모리</th>
        <th scope="col">수행시간</th>
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
                <td> ${submission.state} </td>
                <td> ${submission.memory} </td>
                <td> ${submission.time} </td>
                <td> ${submission.code_length} Bytes </td>
                <td> ${submission.languageName} </td>
                <td> ${submission.date} </td>
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
</html>
