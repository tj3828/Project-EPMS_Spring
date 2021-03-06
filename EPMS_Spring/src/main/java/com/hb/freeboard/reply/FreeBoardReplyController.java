package com.hb.freeboard.reply;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/freeboardReply",method= {RequestMethod.GET,RequestMethod.POST},produces="application/json; charset=utf-8")
public class FreeBoardReplyController {
	
	@Inject
	IFreeBoardReplyService replyservice;
	
	@PostMapping("/replySave.do")
	public void board_ReplyWrite(@RequestBody @Valid FreeBoard_ReplyVO rdto) {
		replyservice.board_ReplyWrite(rdto);
	}
	
	@PostMapping("/replyDelete.do")
	public void board_ReplyDelete(FreeBoard_ReplyVO rdto) {
		replyservice.board_ReplyDelete(rdto);
	}
	
	@PostMapping("/replyEdit.do")
	public void board_ReplyEdit(@RequestBody @Valid FreeBoard_ReplyVO rdto) {
		replyservice.board_ReplyEdit(rdto);
	}
}
