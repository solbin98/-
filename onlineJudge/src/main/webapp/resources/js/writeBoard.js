﻿let allImage = [];
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
            formData.append("images", file);
            formData.append("type", "board");
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
        url: '/images',
        data: formData,
        dataType: 'json',
        processData: false,
        contentType: false,
        cache: false,
        timeout: 6000000,
        success: function(data) {
            allImage.push({ "fileId" : data.fileId, "fileName" : data.fileName });
            let url = '/images/boards/' + data.fileName;
            callback(url, '이미지 추가');
        },
        error: function(e) {
            callback('image_load_fail', '사진 대체 텍스트 입력');
        }
    });
}

function submitProblem(){
    let htmlCode = editor.getHTML();
    let imageNameList = getImageFileNamesStringFromHtmlCode(htmlCode);
    let images = getImageFileIdsFromImageNameList(imageNameList);
    let title = document.getElementById("title").value;
    let tags = document.getElementById("tag-text").value.split("/");
    let input_limit = document.getElementById("input-limit-text").value;
    let output_limit = document.getElementById("output-limit-text").value;
    let memory_limit = document.getElementById("memory-limit-text").value;
    let time_limit = document.getElementById("time-limit-text").value;
    let inputFiles = document.getElementById("inputFile").files;
    let outputFiles = document.getElementById("outputFile").files;
    let difficulty = document.getElementById("difficulty").value;
    let formData = new FormData();
    let testcaseJson = {"testcaseNumber" : inputFiles.length};
    console.log(inputFiles.length);

    for(let i=0;i<inputFiles.length;i++) formData.append("inputFiles", inputFiles[i]);
    for(let i=0;i<inputFiles.length;i++) formData.append("outputFiles", outputFiles[i]);
    for(let i=0;i<images.length;i++) formData.append("images", images[i]);
    for(let i=0;i<testCaseNumber;i++) {
        let testCaseInputText = document.getElementById("testcase-input"+(i+1)).value;
        let testCaseOutputText = document.getElementById("testcase-output"+(i+1)).value;
        testCaseInputText = testCaseInputText.replace(/(\n|\r\n)/g, '<br>');
        testCaseOutputText = testCaseOutputText.replace(/(\n|\r\n)/g, '<br>');
        testcaseJson["input"+(i+1)] = testCaseInputText;
        testcaseJson["output"+(i+1)] = testCaseOutputText;
    }

    formData.append("difficulty", difficulty);
    formData.append('testcases', new Blob([ JSON.stringify(testcaseJson) ], {type : "application/json"}));
    formData.append("memory_limit", memory_limit);
    formData.append("time_limit", time_limit);
    formData.append("input_condition", input_limit);
    formData.append("output_condition", output_limit);
    formData.append("content", htmlCode);
    formData.append("title", title);
    formData.append("tags", tags);
    formData.append("testcase_num", (inputFiles.length).toString())

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
                alert("문제 출제에 성공했습니다.");
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

function submitBoardAjaxForm(method, data, url){
    $.ajax({
        type : method,
        data : data,
        url : url,
        dataType: 'text',
        async:true,
        success : function(resultURL){
            alert("요청에 성공했습니다.");
            location.replace(resultURL);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            alert("요청에 실패했습니다!");
        }
    });
}

// question_id 가 0이면 질문 0이 아니면 답변
function submitAnswer(question_id){
    let htmlCode = editor.getHTML();
    let imageNameList = getImageFileNamesStringFromHtmlCode(htmlCode);
    let images = getImageFileIdsFromImageNameList(imageNameList);
    let url = "/boards/answer";
    let content = editor.getHTML();
    let data = {"content" : content, "question_id" : question_id, "question" : false, "images" : images};
    submitBoardAjaxForm("POST", data, url);
}

// submitAnswer -> boardWritePage 에서 질문 할 때 사용
// board_id 가 0이면 추가 요청 0이 아니면 수정 요청
function submitQuestion(){
    let htmlCode = editor.getHTML();
    let imageNameList = getImageFileNamesStringFromHtmlCode(htmlCode);
    let images = getImageFileIdsFromImageNameList(imageNameList);
    let url = "/boards/question";
    let content = editor.getHTML();
    let problem_id = document.getElementById("problem_id").value;
    let title = document.getElementById("title").value;
    let data = {"content" : content, "problem_id" : problem_id, "question" : true, "title" : title, "images" : images};
    submitBoardAjaxForm("POST", data, url);
}

function updateAnswer(question_id, board_id, member_id){
    let textElement = document.getElementById("submit-title");
    textElement.scrollIntoView();
    textElement.textContent = "답변 수정";
    let contentElement = document.getElementById("content"+board_id);
    editor.setHTML(contentElement.textContent);
    let submitButton = document.getElementById("submit-button");
    submitButton.onclick = function func(){
        let imageNameList = getImageFileNamesStringFromHtmlCode(editor.getHTML());
        let images = getImageFileIdsFromImageNameList(imageNameList);
        let url = "/boards/answer?content=" + editor.getHTML() + "&board_id=" + board_id + "&member_id=" + member_id + "&question_id=" + question_id;
        for(let i=0;i<images.length;i++) url += "&images="+images[i];

        alert("url = " + url);
        submitBoardAjaxForm("PUT", {}, url);
    };
}

function updateQuestion(board_id, member_id){
    let problem_id = document.getElementById("problem_id").value;
    let content = editor.getHTML();
    let title = document.getElementById("title").value;

    let imageNameList = getImageFileNamesStringFromHtmlCode(editor.getHTML());
    let images = getImageFileIdsFromImageNameList(imageNameList);
    let url = "/boards/question?content=" + content + "&title=" + title + "&problem_id=" + problem_id + "&board_id=" + board_id + "&member_id=" + member_id;
    for(let i=0;i<images.length;i++) url += "&images="+images[i];

    submitBoardAjaxForm("PUT", {}, url);
}

// question 이나 board 둘다 삭제할 때 사용하는 함수
function deleteBoard(question_id, board_id, member_id){
    console.log("member_id : " + member_id);
    let url = "/boards?board_id=" + board_id +"&member_id=" + member_id + "&question_id=" + question_id;
    console.log(url);
    submitBoardAjaxForm("DELETE", {}, url);
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

function getImageFileIdsFromImageNameList(images){
    let fileIdList = [];
    for(let i=0;i<images.length;i++){
        for(let j=0;j<allImage.length;j++){
            let imageName = allImage[j]['fileName'];
            let fileId = allImage[j]['fileId'];
            if(imageName == images[i]){
                fileIdList.push(fileId)
                break;
            }
        }
    }
    return fileIdList;
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