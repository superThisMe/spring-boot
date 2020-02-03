package com.example.helloboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping(path = { "/", "/home" })	
	public String home() {
		
		return "Hello, Spring Boot !!!!!";
		
	}
	
}
