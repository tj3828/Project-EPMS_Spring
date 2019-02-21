<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" style="height: 100%;">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <script src="../resources/js/jquery-3.3.1.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="../resources/chat/css/styles.css">
  <script src="../resources/js/jquery-3.3.1.min.js"></script>
  <title>Chat</title>
</head>
<style>
	pre {
		font-family: "Open Sans", sans-serif;
	}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		var chatScroll = "${cookie.chatScroll.value}";
		if(chatScroll == null || chatScroll =="" || chatScroll == "0") {
			$("main").scrollTop($("main")[0].scrollHeight);
		} else {
			$("main").scrollTop(chatScroll);
		}
		
		
	});

	$(window).on('beforeunload', function() {
		var login = "${sessionScope.dto.id}";
		if(login == null || login == "") {
			return false;
		}
		document.cookie = "chatPage=" + escape(3) + "; path=/;";
		document.cookie = "chatWho=" + escape(2) + "; path=/;";
		
		var currentScroll = $('main').scrollTop();
		var opponentNick = "${opponent}";
		document.cookie = "chatOpponent=" + escape(opponentNick) + "; path=/;";
		document.cookie = "chatScroll=" + escape(currentScroll) + "; path=/;";
	});
	
	var webSocket = new WebSocket("ws://118.130.22.175:8081/b/ws");
	webSocket.onopen = function() {
		var opponentNick = "${opponent}";
		webSocket.send("connected with " + opponentNick);
	}
	webSocket.onerror = function() {
		alert('웹소켓 에러');
	} 
	webSocket.onmessage = function(data) {
		if(data.data == "connected Opponent") {
			$('.readcheck').each(function() {
					$(this).remove();
			});
		} else {
			var msg = JSON.parse(data.data);
			var nickname = "${sessionScope.dto.nickname}";
			var opponentNick = "${opponent}";
			if(msg.fromNick == nickname && msg.toNick == opponentNick) {
				$('.chat').append('<div class="chat__message chat__message-from-me">' + 
									  '<font class="readcheck" color="yellow"> 1 &nbsp; </font>' +
						    		  '<span class="chat__message-time">' + msg.writeDate + '</span>' +   
						    		  	'<span class="chat__message-body">' +
		    									msg.content + 
							    		'</span>' +	
							      	  '</span>' + 
					  			  '</div>');
				
			} else if(msg.fromNick == opponentNick && msg.toNick == nickname){
				var path = "${pageContext.request.contextPath}";
				$('.chat').append('<div class="chat__message chat__message-to-me">' + 
					  	'<img src="' + path + '/resources/upload/' + msg.fromNick_profileImg + '" alt="" class="chat-message-avatar">' +
		              	'<div class="chat__message-center">' +
		      				'<h3 class="chat__message-username">'+ msg.fromNick + '</h3>' +
		      				'<span class="chat__message-body">' +
		      					msg.content + 
		      			    '</span>' +
		      			'</div>' +
		      			'<span class="chat__message-time">' + msg.writeDate + '</span>' +
		      		  '</div>');
				
				$.ajax({
					url: "../chatting/chatReading.do",
					data : msg,
					success: function() {
						webSocket.send("connected with " + opponentNick);
					}
				});
			} 
			$("main").scrollTop($("main")[0].scrollHeight);
		}
		
	} 
</script>
<body class="body-chat" style="padding-top: 48px; padding-bottom: 45px; height:100%;">
  <header class="top-header chat-header">
    <div class="header__bottom">
      <div class="header__column">
        <a href="javascript:location.replace('../chat/chats.do');">
          <i class="fa fa-chevron-left fa-lg"></i>
        </a>
      </div>
      <div class="header__column">
        <span class="header__text">Friends</span>
        <span class="header__number">1</span>
      </div>
      <div class="header__column">
      </div>
    </div>
  </header>
<main class="chat" style="overflow-y:scroll; height:100%; animation:none;">
  <c:if test="${list.size() == 0 }">
  	<div class="date-divider">
    	<span class="date-divider__text">대화 내용이 존재하지 않습니다. 채팅을 입력하세요.</span>
  	</div>
  </c:if>
  <c:forEach var="list" items="${list}" begin="0" end="${list.size()}">
  	<c:choose >
  		<c:when test="${list.fromNick == sessionScope.dto.nickname}">
		  <div class="chat__message chat__message-from-me">
		    <span class="chat__message-time">
		    	<c:if test="${list.readCheck == 0}">
		    		<font class="readcheck" color="yellow"> 1 &nbsp; </font>
		    	</c:if>
		    	${list.writeDate}
		    </span>
		    <span class="chat__message-body">
		      ${list.content}
		    </span>
		  </div>
  		</c:when>
  		<c:otherwise>
		  <div class="chat__message chat__message-to-me">
		    <img src="${pageContext.request.contextPath}/resources/upload/${list.fromNick_profileImg}" alt="" class="chat-message-avatar">
		    <div class="chat__message-center">
		      <h3 class="chat__message-username">${list.fromNick}</h3>
		      <span class="chat__message-body">
		        ${list.content}
		      </span>
		    </div>
		    <span class="chat__message-time">${list.writeDate}</span>
		  </div>
  		</c:otherwise>
  	</c:choose>
  </c:forEach>
</main>
<div class="type-message">
  <i class="fa fa-plus fa-lg"></i>
  <div class="type-message__input">
    <textarea class="chatSendMsg" style="resize: none; width: 100%; height: 37.39px; margin-right: 15px; padding: 10px;"></textarea>
    <button class="record-message" style="outline: none;" disabled="disabled">
      <i class="fa fa-chevron-circle-right fa-lg"></i>
    </button>
  </div>
</div>
<div class="bigScreenText">
  Please make your screen smaller
</div>
<script type="text/javascript">
	$('.chatSendMsg').change(function() {
		if($('.chatSendMsg').val() == "" || $('.chatSendMsg').val() == null) {
			$('.record-message').attr("disabled","disabled");
		} else {
			$('.record-message').removeAttr("disabled" );
		}
	});
	$('.record-message').click(function() {
		String.prototype.replaceAll = function(org, dest) {
		    return this.split(org).join(dest);
		}
		var content = $('.chatSendMsg').val();
		var opponentNick = "${opponent}";
		
		content = content.replaceAll('"','\\"');
		var nickname = "${sessionScope.dto.nickname}";
		var profileImg = "${sessionScope.dto.profile_img}";
		var jsonMsg = '{"fromNick":"'+nickname+'", "toNick":"'+ opponentNick +'", "content":"' + content+'", "readCheck":"0" , "fromNick_profileImg" : "' + profileImg + '"}';
		webSocket.send(jsonMsg);
		$('.chatSendMsg').val("");
		$('.chatSendMsg').focus();
		$('.record-message').attr("disabled","disabled");
	});
</script>
</body>
</html>