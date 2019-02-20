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

import com.hb.chat.config.ChatEncoder;
import com.hb.chat.config.HttpSessionConfigurator;

@ServerEndpoint(value="/ws",encoders=ChatEncoder.class,configurator= HttpSessionConfigurator.class)
public class ChatingController {
		
	private static final Logger logger = LoggerFactory.getLogger(ChatingController.class);
	
	@Inject
	IChatService chatService;
	
	private static final Map<String, Session> sessions = new ConcurrentHashMap<String, Session>();
	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		HttpSession httpSession = (HttpSession)config.getUserProperties().get("http");
		String nickname = (String)httpSession.getAttribute("nickname");
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
		ChatVO vo = parseMessage(message);
		logger.debug(">>>>>>>> from : " + vo.getFromNick() + ", 내용 : " + vo.getContent());
		chatService.insertMessage(vo);
		try{
			sendMessage(session, vo);
		} catch(Exception e) {
			System.out.println(e.getMessage());
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
			vo.setReadCheck(Boolean.parseBoolean((String)json.get("readCheck")));
			vo.setWriteDate(new SimpleDateFormat("yy/MM/dd HH:mm").format(new Date()));
		} catch (ParseException e) {
			System.out.println(e.toString());
		}
		
		return vo;
	}
 }