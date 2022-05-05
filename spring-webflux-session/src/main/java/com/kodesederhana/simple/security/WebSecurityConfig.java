package com.kodesederhana.simple.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;
import org.springframework.web.server.session.HeaderWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@EnableRedisWebSession
public class WebSecurityConfig {

	@Bean
    public WebSessionIdResolver webSessionIdResolver() {
        HeaderWebSessionIdResolver sessionIdResolver = new HeaderWebSessionIdResolver();
        sessionIdResolver.setHeaderName("X-AUTH-TOKEN");
        return sessionIdResolver;
    }
	
	@Bean
	protected ReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		UserDetailsRepositoryReactiveAuthenticationManager manager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
		manager.setPasswordEncoder(passwordEncoder);
		return manager;
	}
	
	@Bean
    public WebSessionServerSecurityContextRepository webSessionServerSecurityContextRepository() {
        return new WebSessionServerSecurityContextRepository();
    }
	
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http
			.exceptionHandling()
	        .authenticationEntryPoint(new UnauthorizedHandler())
	        .accessDeniedHandler(new AccessDeniedHandler())
	        .and()
	        .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
			.securityContextRepository(webSessionServerSecurityContextRepository())
			.authorizeExchange()
			.pathMatchers("/login").permitAll()
			.anyExchange().authenticated();
		
		return http.build();
	}
	
}
