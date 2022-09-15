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
    <h2 class="source-code-text"> ${submission_id} 번 제출 / 소스코드</h2>

    <form method="post" action="/submission">
      <textarea class="source-code-div" id="source-code" name="sourceCode"> ${code}</textarea>
    </form>

  </div>
</div>
</body>
</html>
