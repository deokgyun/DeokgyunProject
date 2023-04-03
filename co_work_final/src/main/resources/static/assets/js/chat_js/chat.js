/**
 * web socket
 */

function getId(id){
	return document.getElementById(id);
}
var data = {};//전송 데이터(JSON)

var ws ;
var mid = getId('mid');
var btnLogin = getId('btnLogin');
var btnSend = getId('btnSend');
var talk = getId('talk');
var msg = getId('msg');

//btnLogin.onclick = function(){
	ws = new WebSocket("ws://" + location.host + "/cowork/chat");
	
	
	//서버로부터 메세지를 받는다.
	//s.getBasicRemote().sendText(msg);
	ws.onmessage = function(msg){
		var data = JSON.parse(msg.data);
		var css;
		
		if(data.mid == mid.value){
			css = 'class=me';
		}else{
			css = 'class=other';
		}
		//여기에 화면에 뿌려주는 AJAX 추가해야함
		var item = `<div ${css} >
		                <span><b>${data.mid}</b></span> [ ${data.date} ]<br/>
                      <span>${data.msg}</span>
						</div>`;
					
		talk.innerHTML += item;
		talk.scrollTop=talk.scrollHeight;//스크롤바 하단으로 이동
	}
//}

msg.onkeyup = function(ev){
	if(ev.keyCode == 13){
		send();
	}
}

btnSend.onclick = function(){
	send();
	
}

function send(){
	if(msg.value.trim() != ''){
		data.mid = getId('mid').value;
		data.msg = msg.value;
		data.date = new Date().toLocaleString();
		var temp = JSON.stringify(data);
		ws.send(temp);
	
	$.ajax({
		url : "insert",
		//data : {"ID": $("#loginId").text(),"SENDER": mid.value, "MESSAGE": msg.value},
		data : {"ID": "hta1234","SENDER": mid.value, "MESSAGE": msg.value},
		async: false,   //끝나고나서 일 처리해라
		success : function(){
		console.log("성공");
		
		}
	})
}
	msg.value ='';
	
}


$(document).ready(function() {
    var sender = $("#mid").val();
    
    // 채팅 내역 불러오기
    $.ajax({
        type: "POST",
        url: "${pageContext.request.contextPath}/getChatList",
        data: {},
        success: function(data) {
            var chatList = JSON.parse(data);
            for (var i = 0; i < chatList.length; i++) {
                var chat = chatList[i];
                var html = "<p>" + chat.sender + ": " + chat.message + "</p>";
                $("#talk").append(html);
            }
        },
        error: function() {
            alert("채팅 내역을 불러오는데 실패하였습니다.");
        }
    });

    // 메시지 전송
    $("#btnSend").click(function() {
        var message = $("#msg").val();
        var chatData = {
            "sender": sender,
            "message": message
        };
        
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/insertChat",
            data: chatData,
            success: function(data) {
                $("#msg").val("");
                var html = "<p>" + sender + ": " + message + "</p>";
                $("#talk").append(html);
            },
            error: function() {
                alert("메시지 전송에 실패하였습니다.");
            }
        });
    });
});