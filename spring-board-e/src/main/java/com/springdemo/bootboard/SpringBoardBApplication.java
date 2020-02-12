package com.springdemo.bootboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

@SpringBootApplication(
		// WebMvcConfig에서 Encoding, File Upload 설정을 했기 때문에 자동 설정 제거
		exclude = { HttpEncodingAutoConfiguration.class, MultipartAutoConfiguration.class }
)
public class SpringBoardBApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoardBApplication.class, args);
	}

}
