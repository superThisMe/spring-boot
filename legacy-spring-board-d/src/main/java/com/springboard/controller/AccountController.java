package com.springboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboard.service.MemberService;
import com.springboard.vo.MemberVO;

@Controller // @Component + spring mvc 기능 추가
@RequestMapping(path = { "/account" })
public class AccountController {
	
	@Autowired
	@Qualifier("memberService")
	private MemberService memberService;
	
	@PostMapping(path = { "/register" })
	public String register(MemberVO member) {
		
		memberService.registerMember(member);
		
		return "redirect:/";
	}
	
//	@PostMapping(path = { "/login" })
//	public String login(MemberVO member, HttpSession session) {
//				
//		MemberVO member2 = memberService.findMemberByEmailAndPasswd(member);
//		if (member2 == null) {
//			return "redirect:/resources/login.html";
//		} else {
//			session.setAttribute("loginuser", member2);
//			return "redirect:/";
//		}
//	}
	
//	@GetMapping(path = { "/logout" })
//	public String logout(HttpSession session) {
//		
//		session.removeAttribute("loginuser");
//		
//		return "redirect:/";
//	}
	
	
	
}
