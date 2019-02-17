<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="chatHtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" href="../resources/css/chat.css">
</head>
<style>
	.circle {
		background-color: yellow;
		height: 6em;
		width: 6em;
		border-radius: 7.5em;
		-webkit-border-radius: 7.5em;
		-moz-border-radiud: 7.5e,;
		text-align: center;
		box-shadow: 1px 10px 5px rgba(0,0,0,0.2), 6px 11px 5px rgba(0,0,0,0.2);
	}
	.chatBt {
		border: 0;
		outline: 0;
		cursor: pointer;
	}
	.chatBt:focus {
		outline: none;
	}
	.chatView:after {
	    content: '';
	    position: absolute;
	    border-top: 20px solid ;
	    border-right: 20px solid transparent;
	    border-left: 20px solid transparent;
	    bottom: -20px;
		right: 10px;
	}
</style>
<body style="height: 100%;">
	<div class="chatView" style="position: fixed; height:50%; width:348px; bottom:120px;right:30px; background-color: white; border: 1px solid; box-shadow: 1px 10px 5px rgba(0,0,0,0.2), 6px 11px 5px rgba(0,0,0,0.2);">
		<iframe src="../chat/friend.do" style="overflow-x:hidden; width: 100%; height: 100%;"></iframe>
	</div>
	<button class="chatBt circle"  style="position: fixed; width:66px; height:66px; bottom: 30px; right: 30px;"><i class="fas fa-comment"></i></button> 
</body>
</html>