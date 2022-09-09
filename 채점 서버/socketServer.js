 const express = require('express'); 
 const app = express(); 
 
 app.use("/", (req, res)=>{ 
 	res.sendFile('index.html', { root: __dirname }) 
 }); // index.html 파일 응답
 
 const HTTPServer = app.listen(9001, ()=>{ 
 console.log("Server is open at port:9001"); 
 });
 
 const wsModule = require('ws'); 
 const webSocketServer = new wsModule.Server( 
     { server: HTTPServer, 
         // port: 30002 // WebSocket연결에 사용할 port를 지정한다(생략시, http서버와 동일한 port 공유 사용) 
     } 
 );
 
let websocket;
 
 webSocketServer.on('connection', (ws, request)=>{ 
     websocket = ws;
     const ip = request.headers['x-forwarded-for'] || request.connection.remoteAddress; 
     console.log(`새로운 클라이언트[${ip}] 접속`); 
     
     if(ws.readyState === ws.OPEN){ // 연결 여부 체크 
         ws.send(`클라이언트[${ip}] 접속을 환영합니다 from 서버`); // 데이터 전송 
     }

     ws.on('percent', function(m){
	console.log("precent call");
	ws.send(m);
     });
     
     ws.on('message', (msg)=>{ 
         console.log(`클라이언트[${ip}]에게 수신한 메시지 : ${msg}`); 
         ws.send('메시지 잘 받았습니다! from 서버') 
     }) 
     
     ws.on('error', (error)=>{ 
         console.log(`클라이언트[${ip}] 연결 에러발생 : ${error}`); 
     }) 
     
     ws.on('close', ()=>{ 
         console.log(`클라이언트[${ip}] 웹소켓 연결 종료`); 
     }) 
});

var dgram = require('dgram');
var socket = dgram.createSocket('udp4');
socket.bind(9001);


socket.on('listening', function() {
    console.log('listening event');
});

socket.on('message', function(msg, rinfo) {
    console.log('메세지 도착', rinfo.address, msg.toString());
    websocket.emit('percent', msg.toString());
});

socket.on('close', function() {
    console.log('close event');
});
