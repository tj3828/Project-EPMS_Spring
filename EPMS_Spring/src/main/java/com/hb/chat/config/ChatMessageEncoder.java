package com.hb.chat.config;

import java.io.StringWriter;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.hb.chat.ChatVO;

public class ChatMessageEncoder implements Encoder.Text<ChatVO> {

  @Override
  public String encode(ChatVO chatVO) throws EncodeException {

    StringWriter writer = new StringWriter();
   
    Json.createGenerator(writer)
            .writeStartObject()
              .write("messageType", chatVO.getMessageType())
              .write("fromNick", chatVO.getFromNick())
              .write("toNick", chatVO.getToNick())
              .write("writeDate", chatVO.getWriteDate())
              .write("content", chatVO.getContent().replaceAll("\\\"", "\""))
              .write("readCheck", chatVO.getReadCheck())
              .write("fromNick_profileImg", chatVO.getFromNick_profileImg())
            .writeEnd()
            .flush();
    System.out.println(writer.toString()); 
    return writer.toString();
  }

  @Override
  public void init(EndpointConfig config) {
  }

  @Override
  public void destroy() {
  }
}