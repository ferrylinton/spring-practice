package com.kodesederhana.simple.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodesederhana.simple.entity.User;
import com.kodesederhana.simple.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserRestController {

	private final UserRepository userRepository;

	@GetMapping("/{id}")
	public ResponseEntity<Mono<User>> getByUsername(@PathVariable(value = "id") Long id) {
		Mono<User> user = userRepository
						.findById(id);

		return ResponseEntity.ok().body(user);
	}

}
