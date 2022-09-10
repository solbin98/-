<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="page-number-div-outer">
    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
            <li class="page-item disabled">
                <a class="page-link" href="/problemsList?page=${paging.nowPage-1}">이전 </a>
            </li>
            <c:forEach begin="${paging.leftMostPage}" end="${paging.rightMostPage}" varStatus="page">
                <li class="page-item"><a class="page-link" href="/problemsList?page=${page.current}">${page.current}</a></li>
            </c:forEach>
            <li class="page-item">
                <a class="page-link" href="/problemList?page=${paging.nowPage+1}">다음</a>
            </li>
        </ul>
    </nav>
</div>

</body>
</html>
