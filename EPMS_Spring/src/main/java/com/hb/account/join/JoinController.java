package com.hb.account.join;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hb.account.AccountVO;

@RestController
@RequestMapping("/join")
public class JoinController {
	@Inject
	IJoinService joinService;
	
	@PostMapping("/checkId.do")
	public int checkId(AccountVO dto) {
		return joinService.checkId(dto);
	}
	
	@PostMapping("/checkNick.do")
	public int checkNick(AccountVO dto) {
		return joinService.checkNick(dto);
	}
	
	@PostMapping(value = "/join.do",produces="application/json; charset=utf-8")
	public void join(@RequestPart("serialData") @Valid AccountVO dto, @RequestPart(value="file", required=false) MultipartFile file) {
		dto.setMultipartFile(file);
		joinService.join(dto);
	}
}
