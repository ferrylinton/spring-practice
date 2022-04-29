package com.kodesederhana.simple.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kodesederhana.simple.dto.AuthRequestDto;
import com.kodesederhana.simple.dto.AuthResponseDto;
import com.kodesederhana.simple.security.JWTUtil;
import com.kodesederhana.simple.security.Pbkdf2PasswordEncoder;
import com.kodesederhana.simple.service.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class SecurityRestController {

	private final JWTUtil jwtUtil;

	private final Pbkdf2PasswordEncoder pbkdf2PasswordEncoder;

	private final UserService userService;

	@PostMapping("token")
	public Mono<ResponseEntity<AuthResponseDto>> login(@RequestBody AuthRequestDto dto) {
		return userService.findByUsername(dto.getUsername())
				.filter(user -> pbkdf2PasswordEncoder.encode(dto.getPassword()).equals(user.getPassword()))
				.map(user -> ResponseEntity.ok(new AuthResponseDto(jwtUtil.generateToken(user))))
				.switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
	}

}
