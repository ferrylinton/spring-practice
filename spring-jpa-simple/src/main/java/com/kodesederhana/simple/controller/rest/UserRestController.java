package com.kodesederhana.simple.controller.rest;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodesederhana.simple.entity.User;
import com.kodesederhana.simple.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserRestController {

	private final UserRepository userRepository;

	@GetMapping
	public ResponseEntity<List<User>> getUsers(Pageable pageable) {
		Page<User> page = userRepository.findAll(pageable);
		return ResponseEntity.ok().body(page.getContent());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getByUsername(@PathVariable(value = "id") Long id) {
		User user = userRepository
						.findById(id)
						.orElseThrow(() -> new NoSuchElementException("Data is not found"));

		return ResponseEntity.ok().body(user);
	}

}
