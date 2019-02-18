package com.hb.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatPageController {
	
	@GetMapping("/chats.do")
	public String showChats() {
		return "/common/chat/chats";
	}
	
	@GetMapping("/friend.do")
	public String showFriend() {
		return "/common/chat/index";
	}
	
	@GetMapping("/find.do")
	public String showFind() {
		return "/common/chat/find";
	}
	
	@GetMapping("/chat.do")
	public String showChat() {
		return "/common/chat/chat";
	}
	
	@GetMapping("/more.do")
	public String showMore() {
		return "/common/chat/more";
	}
	
	@GetMapping("/profile.do")
	public String showProfile() {
		return "/common/chat/profile";
	}
}