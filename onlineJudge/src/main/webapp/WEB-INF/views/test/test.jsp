<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
 <script type="text/javascript"
         src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js"></script>
</head>
<body>
 <p> 소켓 통신 테스트 페이지 </p>
 <button onclick="wsSendMessage('hello')"> 메시지 보내기 </button>
</body>

<script>
 let webSocket = new WebSocket("ws://13.209.144.139:9001/");

 let echoText = "123132";
 let message = "aaaaa";

 webSocket.onopen = function(message){ wsOpen(message);};
 webSocket.onmessage = function(message){ wsGetMessage(message);};
 webSocket.onclose = function(message){ wsClose(message);};
 webSocket.onerror = function(message){ wsError(message);};

 function wsOpen(message){
 }

 function wsSendMessage(msg){
  webSocket.send(msg);
 }

 function wsCloseConnection(){
  webSocket.close();
 }

 function wsGetMessage(message){
  let msg = "Message received from to the server : " + message.data + "\n";
  console.log("message : " + msg)
 }

 function wsClose(message){
  echoText.value += "Disconnect ... \n";
  console.log("disconnect", message);
 }

 function wsError(message){
  echoText.value += "Error \n";
  console.log("error", message);
 }

</script>

</html>
