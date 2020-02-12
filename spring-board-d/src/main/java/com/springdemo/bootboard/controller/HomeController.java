package com.springdemo.bootboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping(path = { "/", "/home" })
	public String home() {
		
		return "home";	// /templates/ + home + .html
	}
	
}