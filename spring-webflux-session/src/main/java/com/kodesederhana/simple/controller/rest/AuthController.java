package com.kodesederhana.simple.controller.rest;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;

import com.kodesederhana.simple.dto.LoginDto;
import com.kodesederhana.simple.dto.LoginResponseDto;
import com.kodesederhana.simple.entity.Role;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

	private final ReactiveAuthenticationManager authenticationManager;
	
	private final WebSessionServerSecurityContextRepository securityContextRepository;

	@PostMapping("/login")
	Mono<?> login(@RequestBody LoginDto loginDto, ServerWebExchange exchange){
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(),
				loginDto.getPassword()
        );
		
		return authenticationManager.authenticate(token)
				.flatMap(authentication -> {
					securityContextRepository.save(exchange, new SecurityContextImpl(authentication)).subscribe();
					return exchange.getSession()
							.map(webSession -> {
								
								List<String> authorities = authentication
										.getAuthorities()
										.stream()
									    .map(GrantedAuthority::getAuthority)
									    .collect(Collectors.toList());
								
								Map<String, Object> map = new HashMap<>();
								map.put("token", webSession.getId());
								map.put("username", authentication.getName());
								map.put("authorities", authorities);
								return map;
							});
							
					
//					return securityContextRepository.save(exchange, securityContext)
//							.contextWrite()
//							.map(rs ->{
//								return securityContext.getAuthentication();
//							});
			                
//					return ReactiveSecurityContextHolder.getContext()
//					.map(securityContext -> {
//						securityContextRepository.save(exchange, securityContext).subscribe();
//						return securityContextRepository.load(exchange);
//					});
					
					//return Mono.just(authentication);
//					ReactiveSecurityContextHolder.ge
					
//					return ReactiveSecurityContextHolder
//							.getContext()
//							.map(context -> {
//								
//								return context.getAuthentication().getDetails();
//							});
					
//					return ReactiveSecurityContextHolder.getContext()
//					.map(context -> {
//						securityContextRepository.save(exchange, context).subscribe();
//						return context.;
//					});
//					.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
//					.map(auth -> {
//						return auth;
//					});
					
					//return sessionRepository.findByPrincipalName(authentication.getName());
//					return ReactiveSecurityContextHolder.getContext().
//					return ReactiveSecurityContextHolder.getContext()
//					.map(context -> context.getAuthentication().getPrincipal())
//					.contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
//					.map(auth -> {
//						
//						return auth;
//					});
//					
//					WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
//					
//					LoginResponseDto response = new LoginResponseDto();
//					response.setToken(session.getId());
//					response.setUsername(authentication.getName());
//					return response;
				});
	}
	
    @GetMapping("/user")
    public Mono<Map> current(@AuthenticationPrincipal Mono<Principal> principal) {
        return principal
                .map( user -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", user.getName());
                    map.put("roles", AuthorityUtils.authorityListToSet(((Authentication) user)
                            .getAuthorities()));
                    return map;
                });
    }

    @GetMapping("/logout")
    public Mono<Void> logout(WebSession session) {
        return session.invalidate();
    }

}