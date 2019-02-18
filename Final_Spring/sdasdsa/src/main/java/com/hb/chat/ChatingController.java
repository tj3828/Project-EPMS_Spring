package com.hb.chat;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;

@ServerEndpoint(value="/ws",encoders=ChatEncoder.class,configurator=HttpSessionConfigurator.class)
@Controller
@Singleton
public class ChatingController {
	
	private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		HttpSession httpSession = (HttpSession)config.getUserProperties().get("http");
		System.out.println("websocket 연결 : " + httpSession.getAttribute("id"));
		
		try {
			sessions.add(session);
		}catch (Exception e) {
			System.out.println(e.toString());
			System.out.println(e.getMessage());
			System.out.println("연결 오류");
		}
	}
	
	@OnMessage
	public void onMessage(String message, Session session){
		ChatVO vo = parseMessage(message);
		System.out.println("from : " + session.getId() + ", 내용 : " + vo.getContent());
		try{
			sendMessage(session, vo);
		} catch(Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	@OnError
	public void onError(Throwable t) {
		t.printStackTrace(); 
	}
	
	@OnClose
	public void onClose(Session session) {
		System.out.println("종료 : " + session.getId());
		sessions.remove(session);
	}
	
	public void sendMessage(Session session, ChatVO vo) {
		try {
			for(Session s : sessions) {
				if(!s.getId().equals(session.getId())) {
					//s.getBasicRemote().sendObject(vo);
				}
				s.getBasicRemote().sendObject(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ChatVO parseMessage(String message) {
		JSONParser paser = new JSONParser();
		ChatVO vo = new ChatVO();
		System.out.println(message);
		try {
			JSONObject json = (JSONObject) paser.parse(message);
			System.out.println(json.get("fromNick"));
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










