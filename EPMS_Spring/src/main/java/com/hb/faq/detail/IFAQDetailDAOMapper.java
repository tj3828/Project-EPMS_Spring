package com.hb.faq.detail;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hb.faq.FAQVO;

import config.Mapper;

@Mapper
public interface IFAQDetailDAOMapper {
	
	@Select("select a.num, a.nickname,a.profile_img, a.title, a.content, TO_CHAR(writeDate,'YYYY-MM-DD HH24:MI') as writeDate, a.upload_file, "	+ 
			"		round(upload_file_size/1024,1) as upload_file_size, a.viewCnt, a.parentNum, a.groupNum, a.lev, " + 
			"		(select count(*) from faqboard where parentNum = a.num) as childCnt " + 
			"from ( select  b.*, level as lev from faqboard b " + 
			"		start with b.parentNum = 0 " + 
			"		connect by prior b.num = b.parentNum " + 
			"		order siblings by b.groupNum asc) a "	+ 
			"where num = #{num} or parentNum = #{num} " )
	ArrayList<FAQVO> selectFAQDetailWithchild(FAQVO dto);
	
	@Update("update faqboard set viewCnt = viewCnt + 1 where num = #{num}")
	void updateViewCount(FAQVO dto);
}