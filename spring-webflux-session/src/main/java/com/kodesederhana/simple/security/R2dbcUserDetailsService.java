package com.kodesederhana.simple.security;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.kodesederhana.simple.repository.UserRepository;
import com.kodesederhana.simple.service.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class R2dbcUserDetailsService implements ReactiveUserDetailsService {

	private final UserService userService;

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		return userService.findByUsername(username)
				.map(user -> {
					
					Set<GrantedAuthority> roles = new HashSet<>();
					if(user.getRoles() != null && user.getRoles().size() > 0) {
						roles = user.getRoles().stream()
								.map(role -> { return new SimpleGrantedAuthority(role); })
								.collect(Collectors.toSet());
					}
					
					return new AuthenticatedUser(username, user.getPassword(), true, true, roles);
				});
	}
	
}
