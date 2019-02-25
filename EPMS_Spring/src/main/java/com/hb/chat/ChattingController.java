package com.hb.chat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hb.account.AccountVO;
import com.hb.chat.config.ChatEncoder;
import com.hb.chat.config.HttpSessionConfigurator;

@Controller
@RequestMapping("/chatting")
@ServerEndpoint(value="/ws",encoders=ChatEncoder.class,configurator= HttpSessionConfigurator.class)
public class ChattingController {
		
	private static final Logger logger = LoggerFactory.getLogger(ChattingController.class);
	
	@Inject
	IChatService chatService;
	
	private static final Map<String, Session> sessions = new ConcurrentHashMap<String, Session>();
	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		HttpSession httpSession = (HttpSession)config.getUserProperties().get("http");
		String nickname = ((AccountVO)httpSession.getAttribute("dto")).getNickname();
		System.out.println("=================== webSocket 연결 ===================");
		
		try {
			sessions.put(nickname,session);
			System.out.println(">>>>>>>> 현재 접속자 : " + sessions.toString());
		}catch (Exception e) {
			System.out.println(e.toString());
			System.out.println(e.getMessage());
			System.out.println("연결 오류");
		}
	}
	
	@OnMessage
	public void onMessage(String message, Session session){
		if(message.startsWith("connected with")) {
			String opponentNick = message.split(" ")[2];
			try {
				for(String s : sessions.keySet()) {
					if(s.equals(opponentNick)) {
						for(String nickname : sessions.keySet()) {
							if(sessions.get(nickname).getId() == session.getId()) {
								chatService.updateMessageRead(nickname, opponentNick);
								sendMessageRead(opponentNick);														
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			ChatVO vo = parseMessage(message);
			System.out.println(">>>>>>>> from : " + vo.getFromNick() + ", 내용 : " + vo.getContent() + ", 읽음 체크 : " + vo.getReadCheck());
			chatService.insertMessage(vo);
			try{
				sendMessage(session, vo);
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	@OnError
	public void onError(Throwable t) {
		t.printStackTrace(); 
	}
	
	@OnClose
	public void onClose(Session session) {
		synchronized (sessions) {
			for(Iterator<String> it = sessions.keySet().iterator(); it.hasNext();) {
				try {
					String nick = it.next();
					if(sessions.get(nick).getId().equals(session.getId())) {
						sessions.remove(nick);				
						 System.out.println(">>>>>>>> 웹소켓 종료 후 남은 사용자 : " + sessions.toString());
					}
				} catch (Exception e) {
					System.out.println("웹소켓 닫힘 오류");
				}
			}
		}
		System.out.println("=================== webSocket 닫힘 ===================");
	}
	
	// 메세지 읽음 상태 전송
	public void sendMessageRead(String opponentNick) {
		try {
			for(String s : sessions.keySet()) {
				if(s.equals(opponentNick)) {
					sessions.get(s).getBasicRemote().sendText("connected Opponent");;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 메세지 전송
	public void sendMessage(Session session, ChatVO vo) {
		try {
			for(String s : sessions.keySet()) {
				if(s.equals(vo.getFromNick()) || s.equals(vo.getToNick())) {
					sessions.get(s).getBasicRemote().sendObject(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 메세지 파싱 작업
	public ChatVO parseMessage(String message) {
		JSONParser paser = new JSONParser();
		ChatVO vo = new ChatVO();
		try {
			JSONObject json = (JSONObject) paser.parse(message);
			vo.setFromNick((String)json.get("fromNick"));
			vo.setToNick((String)json.get("toNick"));
			String content = (String)json.get("content");
			content = content.replaceAll("\n", "<br>");
			vo.setContent(content);
			vo.setReadCheck(Integer.parseInt((String)json.get("readCheck")));
			vo.setFromNick_profileImg((String)json.get("fromNick_profileImg"));
			vo.setWriteDate(new SimpleDateFormat("yy/MM/dd HH:mm").format(new Date()));
		} catch (ParseException e) {
			System.out.println(e.toString());
		}
		
		return vo;
	}
	
	@ResponseBody
	@GetMapping("/chatReading.do")
	public void chatReading(ChatVO dto) {
		chatService.updateMessageRead(dto.getFromNick(), dto.getToNick());
	}
	
	@GetMapping("/chatAllDelete.do")
	public String chatAllDelete(HttpSession session, ChatVO dto) {
		AccountVO vo = (AccountVO)session.getAttribute("dto");
		if(vo != null) {
			dto.setFromNick(vo.getNickname());
		}
		chatService.deleteMessageAll(dto);
		return "redirect:/chat/chats.do";
	}
	
	@ResponseBody
	@GetMapping("/chatUserSearch.do")
	public int chatUserSearch(@RequestParam(value="nickname", defaultValue=" ") String nickname) {
		return chatService.selectChatUserSearch(nickname);
	}
 }