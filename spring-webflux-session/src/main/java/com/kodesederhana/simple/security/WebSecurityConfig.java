package com.kodesederhana.simple.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http
			.authorizeExchange()
			.anyExchange()
			.authenticated()
		.and()
			.httpBasic()
		.and()
			.formLogin();
		
		return http.build();
	}
}
