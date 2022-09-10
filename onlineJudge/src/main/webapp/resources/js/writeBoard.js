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
            allImage.push(data.name);
            let url = '/images/boards/' + data.name;
            callback(url, '이미지 추가');
        },
        error: function(e) {
            callback('image_load_fail', '사진 대체 텍스트 입력');
        }
    });
}

function submitProblem(){
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
    let difficulty = document.getElementById("difficulty").value;
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

    formData.append("difficulty", difficulty);
    formData.append('testcases', new Blob([ JSON.stringify(testcaseJson) ], {type : "application/json"}));
    formData.append("memory_limit", memory_limit);
    formData.append("time_limit", time_limit);
    formData.append("input_condition", input_limit);
    formData.append("output_condition", output_limit);
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

function submitQuestion(type){
    let question_id = "";
    let content = editor.getHTML();
    let problem_id = document.getElementById("problem_id").value;
    let title = document.getElementById("title").value;
    let data = {"content" : content, "problem_id" : problem_id, "question" : 1, "title" : title};
    if(type == "answer") {
        question_id = document.getElementById("question_id");
        data["question"] = question_id;
    }

    $.ajax({
        type : "POST",
        url : "/boards/question",
        data : data,
        dataType: 'json',
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