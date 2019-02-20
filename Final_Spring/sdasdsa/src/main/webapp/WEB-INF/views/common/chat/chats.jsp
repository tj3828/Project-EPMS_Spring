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
  <title>Chats</title>
</head>
<style>
	pre {
		font-family: "Open Sans", sans-serif;
	}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		var chatScroll = "${cookie.chatScroll.value}";
		if(chatScroll == null || chatScroll =="" ) {
			$(window).scrollTop(0);
		} else {
			$(window).scrollTop(chatScroll);
		}
		
		
	});

	$(window).on('beforeunload', function() {
		var login = "${sessionScope.id}";
		if(login != null && login != "") {	
			document.cookie = "chatPage=" + escape(2) + "; path=/;";
			
			var currentScroll = $('main').scrollTop();
			document.cookie = "chatScroll=" + escape(currentScroll) + "; path=/;";
		}
	});
</script>
<body style="padding-top: 46px; padding-bottom: 45px; height:100%;">
  <header class="top-header">
  	
    <div class="header__bottom">
      <div class="header__column">
      </div>
      <div class="header__column">
        <span class="header__text">Friends</span>
        <span class="header__number">1</span>
      </div>
      <div class="header__column">
      </div>
    </div>
  </header>
  <main class="chats" style="overflow-y:scroll; height:100%; animation: none;" >
    <div class="search-bar">
      <i class="fa fa-search"></i>
      <input type="text" placeholder="Find friends, chats, Plus Friends">
    </div>
    <ul class="chats__list">
	  	<c:forEach var="list" items="${list}" begin="0" end="${list.size()}">
	  		<c:choose>
	  			<c:when test="${list.fromNick == sessionScope.nickname}">
	  				<c:set var="opponentNick" value="${list.toNick}"/>
	  			</c:when>
	  			<c:otherwise>
	  				<c:set var="opponentNick" value="${list.fromNick }"/>
	  			</c:otherwise>
	  		</c:choose>
	  		<li class="chats__chat">
		        <a href="javascript:location.replace('../chat/chat.do?opponent=${opponentNick}');">
		          <div class="chat__content">
		            <img src="../resources/chat/images/avatar.png">
		            <div class="chat__preview">
		                <h3 class="chat__user">${opponentNick}</h3>
		                <span class="chat__last-message">${list.content}</span>
		            </div>
		          </div>
		          <span class="chat__date-time">
		            ${list.writeDate}
		          </span>
		        </a>
		      </li>
	  	</c:forEach>
    </ul>
    <div class="chat-btn" style="cursor: pointer;">
      <i class="fa fa-comment"></i>
    </div>
  </main>
  <nav class="tab-bar">
    <a href="javascript:location.replace('../chat/friend.do')" class="tab-bar__tab">
      <i class="fa fa-user"></i>
      <span class="tab-bar__title">Friends</span>
    </a>
    <a href="javascript:location.replace('../chat/chats.do')" class="tab-bar__tab tab-bar__tab--selected">
      <i class="fa fa-comment"></i>
      <span class="tab-bar__title">Chats</span>
    </a>
    <a href="javascript:location.replace('../chat/find.do')" class="tab-bar__tab">
      <i class="fa fa-search"></i>
      <span class="tab-bar__title">Find</span>
    </a>
    <a href="javascript:location.replace('../chat/more.do')" class="tab-bar__tab">
      <i class="fa fa-ellipsis-h"></i>
      <span class="tab-bar__title">More</span>
    </a>
  </nav>
  <div class="bigScreenText">
    Please make your screen smaller
  </div>
  <script type="text/javascript">
  	$('.chat-btn').click(function() {
  		var opponentNick = prompt( '닉네임 입력', '' );
  		if(opponentNick == null) {
  			return;	
  		}
  		location.replace("../chat/chat.do?opponent=" + opponentNick);
	});
  </script>
</body>
</html>
