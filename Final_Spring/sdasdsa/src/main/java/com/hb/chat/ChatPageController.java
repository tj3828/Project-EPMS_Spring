package com.hb.chat;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/chat")
public class ChatPageController {
	
	@Inject
	IChatService chatService;
	
	@GetMapping("/chats.do")
	public String showChats(HttpSession session, Model model) {
		String id = (String)session.getAttribute("id");
		ArrayList<ChatVO> list = chatService.selectChatsList(id);
		model.addAttribute("list",list);
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
	public String showChat(HttpSession session, @RequestParam("opponent") String opponent, Model model) {
		String id = (String)session.getAttribute("id");
		ArrayList<ChatVO> list = chatService.selectMessageList(id,opponent);
		model.addAttribute("list",list);
		model.addAttribute("opponent", opponent);
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
