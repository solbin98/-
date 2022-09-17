<%--@elvariable id="LoginInfoData" type="LoginInfoData"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link href="/resources/css/login.css" rel="stylesheet" type="text/css" />
    <link href="/resources/css/join.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <%@include file="/resources/jsp/header.jsp" %>
    <div class="p-5 bg-image" style="background-image: url('/resources/png/login-background.jpg');height: 100%;"></div>
    <div class="login-div">
        <section class="text-center">
            <div class="card mx-4 mx-md-5 shadow-5-strong" style="margin-top: -90%;background: hsla(0, 0%, 100%, 0.8);backdrop-filter: blur(30px); ">
                <div class="card-body py-5 px-md-5">
                    <div class="row d-flex justify-content-center">
                        <div class="col-lg-8">
                            <h2 class="fw-bold mb-5"> <spring:message code="text.websiteTitle"></spring:message></h2>
                                <form:form modelAttribute="LoginInfoData" method="post" action="/login">
                                    <div class="form-outline mb-4">
                                        <input name="username" type="id" class="form-control" placeholder="<spring:message code="text.id"></spring:message>" value="${username}"/>
                                        <p class="error-text-small">${error}</p>
                                    </div>

                                    <!-- Password input -->
                                    <div class="form-outline mb-4">
                                        <input name="password" type="password" class="form-control" placeholder="<spring:message code="text.password"></spring:message>" value="${password}" />
                                    </div>

                                    <!-- Submit button -->
                                    <button type="submit" class="btn btn-primary btn-block mb-4"><spring:message code="text.loginButton"></spring:message></button>

                                    <!-- Register buttons -->
                                    <div class="text-center">
                                        <button type="button" class="btn btn-link btn-floating mx-1">
                                            <a href="/join" class="fab fa-facebook-f"> <spring:message code="text.joinText"></spring:message> </a>
                                        </button>
                                    </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</body>
</html>
