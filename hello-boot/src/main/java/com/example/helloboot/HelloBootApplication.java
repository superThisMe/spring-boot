package com.example.helloboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // @EnableAutoConfiguration, @ComponentScan, @Configuration
public class HelloBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloBootApplication.class, args);
	}

}
