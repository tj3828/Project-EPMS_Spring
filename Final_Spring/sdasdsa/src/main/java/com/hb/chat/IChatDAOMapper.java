package com.hb.chat;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import config.Mapper;

@Mapper
public interface IChatDAOMapper {
	
	@Insert("insert into chatting values("
			+ "chatting_seq.nextval, "
			+ "#{fromNick}, "
			+ "#{toNick},"
			+ "to_date(#{writeDate},'YY/MM/DD HH24:MI'),"
			+ "#{content},"
			+ "#{readCheck}"
			+ ")")
	void insertMessage(ChatVO dto);
	
	@Select("select num, fromNick, toNick, to_char(writeDate,'YY/MM/DD HH24:MI') as writeDate, content, readCheck from ( "
			+ 	"select * from chatting "
			+ 		"where (fromNick = #{id} and toNick = #{opponent}) or (fromNick = #{opponent} and toNick = #{id}) "
			+ ") order by num asc")
	ArrayList<ChatVO> selectMessageList(@Param("id") String id, @Param("opponent") String opponent);
	
	@Select("select num, fromNick, toNick, to_char(writeDate,'YY/MM/DD HH24:MI') as writeDate, content, readCheck from ( "
			+ 	"select chatting.* from ( "
			+ 		"select fromNick, toNick, MAX(num) as num from chatting "
			+ 			"group by fromNick, toNick "
			+ 			"having fromNick = #{id} or toNick = #{id} "
			+ 	") c inner join chatting on c.num = chatting.num "
			+ ") order by writeDate desc ")
	ArrayList<ChatVO> selectChatsList(@Param("id") String id);
}