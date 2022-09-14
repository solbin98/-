<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="/resources/css/problemPage.css" rel="stylesheet" type="text/css" />
    <link href="/resources/css/submissionPage.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <%@include file="/resources/jsp/header.jsp" %>
    <div class="content-side">
        <div class="board-block-full">
            <h2 class="source-code-text"> ${problem_id}번 문제 / 소스코드 제출</h2>

            <form method="post" action="/submission">
                <div class="submit-language">
                    <h4> 제출 언어 </h4>
                    <select class="submit-language-select" value="select" name="language_id">
                        <c:forEach items="${languages}" var="language">
                            <option value="${language.language_id}">${language.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <textarea class="source-code-div" id="source-code" name="sourceCode"></textarea>
                <input name="problem_id" value="${problem_id}" hidden="true"></input>
                <button class="submit-button" type="submit"> 코드 제출 </button>
            </form>

        </div>
    </div>
</body>
</html>
