<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="/resources/css/problemWritePage.css">
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
    <!-- katex 추가하는 부분 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/katex@0.11.1/dist/katex.min.css" integrity="sha384-zB1R0rpPzHqg7Kpt0Aljp8JPLqbXI3bhnPWROx27a9N0Ll6ZP/+DiW/UqRcLbRjq" crossorigin="anonymous"/>
    <script defer src="https://cdn.jsdelivr.net/npm/katex@0.11.1/dist/katex.min.js" integrity="sha384-y23I5Q6l+B6vatafAwxRu/0oK/79VlbSz7Q9aiSZUvyWYIYsd+qj+o24G5ZU2zJz" crossorigin="anonymous"></script>
    <script defer src="https://cdn.jsdelivr.net/npm/katex@0.11.1/dist/contrib/auto-render.min.js" integrity="sha384-kWPLUVMOks5AQFrykwIup5lo0m3iMkkHrD0uJ4H5cjeGihAutqP0yW0J6dpFiVkI" crossorigin="anonymous" onload="renderMathInElement(document.body);"></script>
    <script>
        let options = {
            delimiters: [
                { left: "$$", right: "$$", display: true },
                { left: "$", right: "$", display: false },
            ],
            throwOnError : false
        }
        document.addEventListener("DOMContentLoaded", function () {
            //renderMathInElement(document.body, options);
        });
    </script>
    <!-- katex 끝 -->
</head>
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script src="//code.jquery.com/jquery.min.js"></script>
<body>
<h2 class="title-text"> <spring:message code="problemWritePage.text.title"></spring:message> </h2>
<input class="title" type="text" placeholder="<spring:message code="problemWritePage.input.text.title"></spring:message>" id="title" value="${title}">
<div class="editor" id="editor"></div>

<div class="tag-box">
    <h3 class="tag-text"> <spring:message code="problemWritePage.text.tag"></spring:message> </h3>
    <input class="tag-input" type="text" placeholder="<spring:message code="problemWritePage.input.text.tag"></spring:message>" id="tag-text" value="${tags}">
</div>

<div class="tag-box">
    <h3 class="tag-text"> <spring:message code="problemWritePage.text.timeLimit"></spring:message> </h3>
    <input class="limit-text-input" type="text" placeholder="<spring:message code="problemWritePage.input.text.timeLimit"></spring:message>" id="time-limit-text" value="">
</div>

<div class="tag-box">
    <h3 class="tag-text"> <spring:message code="problemWritePage.text.memoryLimit"></spring:message> </h3>
    <input class="limit-text-input" type="text" placeholder="<spring:message code="problemWritePage.input.text.memoryLimit"></spring:message>" id="memory-limit-text" value="">
</div>

<div class="tag-box">
    <h3 class="tag-text"> <spring:message code="problemWritePage.text.inputLimit"></spring:message> </h3>
    <input class="tag-input" type="text" placeholder="<spring:message code="problemWritePage.input.text.inputLimit"></spring:message>" id="input-limit-text" value="">
</div>

<div class="tag-box">
    <h3 class="tag-text"> <spring:message code="problemWritePage.text.outputLimit"></spring:message> </h3>
    <input class="tag-input" type="text" placeholder="<spring:message code="problemWritePage.input.text.outputLimit"></spring:message>" id="output-limit-text" value="">
</div>

<div class="testcase-box">
    <h3 class="tag-text"> <spring:message code="problemWritePage.text.testcase"></spring:message> </h3>
    <button class="testcase-add-button" onclick="createTestCaseElements()" class="submit-button"> 케이스 추가 </button>
    <p></p>
    <div id="testcase-div"><!-- 동적으로 테스트 케이스 박스들이 추가되는 곳 --></div>
</div>

<div class="testcase-box">
    <h3>Input 파일 추가하기</h3>
    <input name="input" type="file" class="profile-image-select-box" id="inputFile" multiple  accept="multipart/form-data" onchange="loadFile(this)">
    <h3>Output 파일 추가하기</h3>
    <input name="output" type="file" class="profile-image-select-box" id="outputFile" multiple  accept="multipart/form-data" onchange="loadFile(this)">
</div>

<div class="submit-button-div">
    <button onclick="submit()" class="submit-button" id="submit-button"> <spring:message code="problemWritePage.button.text.submit"></spring:message> </button>
</div>

<div class="submit-button-div">
    <button onclick="submitTest()" class="submit-button"> 게시글 미리보기 </button>
</div>

<p id="preview-Page"></p>
</body>


<script>
    let allImage = [];
    let testCaseNumber = 0;

    const Editor = toastui.Editor;
    const editor = new Editor({
        el: document.querySelector('#editor'),
        height: '500px',
        initialEditType: 'markdown',
        previewStyle: 'vertical',
        customHTMLRenderer: {
            latex(node) {
                const generator = new latexjs.HtmlGenerator({ hyphenate: false });
                const { body } = latexjs.parse(node.literal, { generator }).htmlDocument();

                return [
                    { type: 'openTag', tagName: 'div', outerNewLine: true },
                    { type: 'html', content: body.content },
                    { type: 'closeTag', tagName: 'div', outerNewLine: true }
                ];
            },
        },
        hooks: {
            addImageBlobHook: (file, callback) => {
                const formData = new FormData();
                formData.append("image", file);
                sendImage(formData, callback);
            }
        }
    });

    function submitTest(){
        document.getElementById("preview-Page").innerHTML = editor.getHTML();
        renderMathInElement(document.getElementById("preview-Page"), options);
    }

    function sendImage(formData, callback){
        $.ajax({
            type: 'POST',
            url: '/boards/image',
            data: formData,
            dataType: 'json',
            processData: false,
            contentType: false,
            cache: false,
            timeout: 6000000,
            success: function(data) {
                allImage.push(data.name);
                let url = '/images/' + data.name;
                callback(url, '이미지 추가');
            },
            error: function(e) {
                callback('image_load_fail', '사진 대체 텍스트 입력');
            }
        });
    }

    function submit(){
        let htmlCode = editor.getHTML();
        let images = getImageFileNamesStringFromHtmlCode(htmlCode);
        let title = document.getElementById("title").value;
        let tags = document.getElementById("tag-text").value.split("/");
        let input_limit = document.getElementById("input-limit-text").value;
        let output_limit = document.getElementById("output-limit-text").value;
        let memory_limit = document.getElementById("memory-limit-text").value;
        let time_limit = document.getElementById("time-limit-text").value;
        let inputFiles = document.getElementById("inputFile").files;
        let outputFiles = document.getElementById("outputFile").files;

        let formData = new FormData();
        let testcaseJson = {"testcaseNumber" : testCaseNumber};

        for(let i=0;i<inputFiles.length;i++) formData.append("inputFiles", inputFiles[i]);
        for(let i=0;i<inputFiles.length;i++) formData.append("outputFiles", outputFiles[i]);
        for(let i=0;i<images.length;i++) formData.append("images", images[i]);
        for(let i=0;i<testCaseNumber;i++) {
            let testCaseInputText = document.getElementById("testcase-input"+(i+1)).value;
            let testCaseOutputText = document.getElementById("testcase-output"+(i+1)).value;
            testcaseJson["input"+(i+1)] = testCaseInputText;
            testcaseJson["output"+(i+1)] = testCaseOutputText;
        }
        formData.append('testcases', new Blob([ JSON.stringify(testcaseJson) ], {type : "application/json"}));
        formData.append("memory_limit", memory_limit);
        formData.append("time_limit", time_limit);
        formData.append("input_limit", input_limit);
        formData.append("output_limit", output_limit);
        formData.append("content", htmlCode);
        formData.append("title", title);
        formData.append("tags", tags);

        $.ajax({
            type : "POST",
            url : "/problems",
            data : formData,
            dataType: 'json',
            processData: false,
            contentType: false,
            cache: false,
            enctype : 'multipart/form-data',
            timeout: 6000000,
            success : function(res){
                console.log(res);
                if(res.resultCode) {
                    alert("문제 제작에 성공했습니다.");
                    location.replace("/main");
                }
                else{
                    let errorMessage = res.errorMessage;
                    alert(errorMessage);
                }
            },
            error : function(XMLHttpRequest, textStatus, errorThrown){
                alert("통신 실패");
            }
        });
    }

    function getImageFileNamesStringFromHtmlCode(htmlCode){
        let names = [];
        let tmpDoc = document.createElement('div');
        tmpDoc.innerHTML = htmlCode;
        let images = tmpDoc.getElementsByTagName("img");
        for(let i=0;i<images.length;i++){
            let src = images[i].src;
            let imageFileName = "";
            for(let i=src.length-1;i>=0;i--) {
                if(src[i] == "/") break;
                imageFileName += src[i];
            }
            imageFileName = imageFileName.split('').reverse().join('');
            names.push(imageFileName);
        }
        return names;
    }

    function createTestCaseElements(){
        testCaseNumber++;

        let testCaseHeaderText = document.createElement("p");
        let testCaseInputText = document.createElement("textarea");
        let testCaseOutputText = document.createElement("textarea");

        testCaseHeaderText.className = "tag-text";
        testCaseHeaderText.textContent="테스트 케이스"+testCaseNumber;

        testCaseInputText.className="tag-input";
        testCaseInputText.placeholder="테스트 케이스 입력" + testCaseNumber;
        testCaseInputText.id="testcase-input"+testCaseNumber;

        testCaseOutputText.className="tag-input";
        testCaseOutputText.placeholder="테스트 케이스 출력" + testCaseNumber;
        testCaseOutputText.id="testcase-output"+testCaseNumber;


        let div = document.getElementById("testcase-div");
        div.appendChild(testCaseHeaderText);
        div.appendChild(testCaseInputText);
        div.appendChild(testCaseOutputText);
    }

</script>
</html>
