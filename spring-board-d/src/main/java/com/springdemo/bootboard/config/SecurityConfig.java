package com.springdemo.bootboard.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.springdemo.bootboard.security.SpringBoardNoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	
	@Bean
	public SpringBoardNoOpPasswordEncoder passwdEncoder() {
		
		return new SpringBoardNoOpPasswordEncoder();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
				
		super.configure(web);		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		http.authorizeRequests()
//			.antMatchers("/").permitAll()
//			.antMatchers("/home").permitAll()
//			.antMatchers("/css/**").permitAll()
//			.antMatchers("/**").authenticated();		
//		http.formLogin();
//		http.logout();	
//		http.csrf().disable();
		
		http	.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/home").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/**").authenticated()
			.and()
				.formLogin()
			.and()
				.logout()
			.and()
				.csrf().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
//		auth.inMemoryAuthentication()
//			.withUser("apprentice91@gmail.com").password("{noop}1234").roles("MEMBER").and()
//			.withUser("silveriane@naver.com").password("{noop}1234").roles("ADMIN");
		
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("SELECT user_name, passwd, enabled FROM tbl_member where user_name = ?")
			.authoritiesByUsernameQuery("SELECT user_name, role_name FROM tbl_member_role where user_name = ?")
			.passwordEncoder(passwdEncoder());
		
	}
	
}
