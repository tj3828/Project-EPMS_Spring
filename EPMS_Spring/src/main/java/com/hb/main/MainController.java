package com.hb.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainController {
	
	@GetMapping("/main.do")
	public String viewMainPage() {
		return "/common/main";
	}

	@GetMapping("/intro.do")
	public String viewIntroPage() {
		return "/common/intro";
	}
}
