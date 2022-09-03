<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
<link rel="stylesheet" href="/resources/css/problemListPage.css">
<link href="/resources/css/login.css" rel="stylesheet" type="text/css" />
<link href="/resources/css/join.css" rel="stylesheet" type="text/css" />
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%@include file="/resources/jsp/header.jsp" %>
    <div class="problem-list-table">
        <table class="table table-striped">
            <thead>
                <th scope="col">번호</th>
                <th scope="col">난이도</th>
                <th scope="col">제목</th>
                <th scope="col">분류</th>
                <th scope="col">제출</th>
                <th scope="col">맞힌 사람</th>
            </thead>
            <tr>
                <td> 1 </td>
                <td> 아주쉬움 </td>
                <td> A + B </td>
                <td> 구현/수학 </td>
                <td> 32323 </td>
                <td> 31233 </td>
            </tr>
            <tr>
                <td> 2 </td>
                <td> 아주쉬움 </td>
                <td> A - B </td>
                <td> 구현/수학 </td>
                <td> 52323 </td>
                <td> 42323 </td>
            </tr>
            <tr>
                <td> 3 </td>
                <td> 아주쉬움 </td>
                <td> A x B </td>
                <td> 구현/수학 </td>
                <td> 52323 </td>
                <td> 42323 </td>
            </tr>
            <tr>
                <td> 4 </td>
                <td> 아주쉬움 </td>
                <td> A / B </td>
                <td> 구현/수학 </td>
                <td> 52323 </td>
                <td> 42323 </td>
            </tr>
        </table>
    </div>
    <div class="page-number-div-outer">
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center">
                <li class="page-item disabled">
                    <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
                </li>
                <li class="page-item"><a class="page-link" href="#">1</a></li>
                <li class="page-item">
                    <a class="page-link" href="#">Next</a>
                </li>
            </ul>
        </nav>
    </div>
</body>
</html>
