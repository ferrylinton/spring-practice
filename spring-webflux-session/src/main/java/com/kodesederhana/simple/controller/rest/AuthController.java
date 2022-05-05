package com.kodesederhana.simple.controller.rest;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;

import com.kodesederhana.simple.dto.LoginDto;
import com.kodesederhana.simple.security.AuthenticatedUser;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
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
				});
	}
	
	@PreAuthorize("hasRole('ROLE_TEST1')")
    @GetMapping("/info")
    public Mono<?> current(@AuthenticationPrincipal Mono<UserDetails> userDetails) {
        return userDetails
                .map( user -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("username", user.getUsername());
                    map.put("roles", AuthorityUtils.authorityListToSet(((AuthenticatedUser) user).getAuthorities()));
                    return map;
                });
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/denied")
    public Mono<?> denied(@AuthenticationPrincipal Mono<UserDetails> userDetails) {
        return userDetails
                .map( user -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("username", user.getUsername());
                    map.put("roles", AuthorityUtils.authorityListToSet(((AuthenticatedUser) user).getAuthorities()));
                    return map;
                });
    }

    @GetMapping("/logout")
    public Mono<?> logout(Mono<Principal> monoPrincipal, WebSession session) {
    	return monoPrincipal
    			.map(principal -> {
    				session.invalidate().subscribe();
    				return Map.of("message", "User ['" + principal.getName() +  "'] is logout");
    			});         
    }

}