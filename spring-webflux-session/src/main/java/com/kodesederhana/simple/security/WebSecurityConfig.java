package com.kodesederhana.simple.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;
import org.springframework.web.server.session.HeaderWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

import com.kodesederhana.simple.service.UserService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
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
	
//	@Bean
//    public ReactiveUserDetailsService userDetailsService(UserService userService) {
//
//        return username -> userService.findByUsername(username)
//                .map(u -> User
//                        .withUsername(u.getUsername()).password(u.getPassword())
//                        .authorities(u.getRoles().toArray(new String[0]))
//                        .accountExpired(false)
//                        .credentialsExpired(false)
//                        .disabled(false)
//                        .accountLocked(false)
//                        .build()
//                );
//    }
	
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http
			.csrf(it -> it.disable())
			//.httpBasic(it -> it.securityContextRepository(new WebSessionServerSecurityContextRepository()))
			.securityContextRepository(webSessionServerSecurityContextRepository())
			.authorizeExchange()
			.pathMatchers("/auth/login").permitAll()
			.anyExchange().authenticated();
		
		return http.build();
	}
}
