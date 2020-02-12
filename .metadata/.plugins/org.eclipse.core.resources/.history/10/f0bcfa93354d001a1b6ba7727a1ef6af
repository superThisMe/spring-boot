package com.springdemo.bootboard.config;

import java.nio.charset.Charset;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {	// 이 설정 class가 Spring MVC 설정 수행

	@Bean
	public Filter characterEncodingFilter() {
		
		// Web Request/Response에 대한 Encoding 설정
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		return filter;
	}
	
	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		
		// @ResponseBody Response에 대한 Encoding 설정
		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
	}
	
	// Apache Commons FileUpload Library를 사용해서 File Upload 처리를 수행하는 Bean을 등록
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setMaxUploadSizePerFile(1024 * 1024 * 5);
		return resolver;
	}
	
}
