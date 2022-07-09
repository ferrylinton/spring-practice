package com.kodesederhana.simplemongodb.restcontroller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodesederhana.simplemongodb.model.User;
import com.kodesederhana.simplemongodb.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {

	private final UserRepository userRepository;

	@GetMapping("/users")
	public Flux<?> findAll(Pageable pageable) {
		return userRepository.findAll();
	}

	@GetMapping("/users/{id}")
	public Mono<?> findById(@PathVariable("id") String id) {
		return userRepository.findById(id)
				.map(user -> ResponseEntity.ok(user))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping("/users")
	public Mono<?> create(@RequestBody User user) {
		return userRepository.save(user);
	}

	@PutMapping("/users/{id}")
	public Mono<?> update(@PathVariable("id") String id, @RequestBody User user) {
		return userRepository.findById(id)
				.flatMap(current -> {
			user.setId(current.getId());
			return userRepository.save(user);
		}).map(updated -> ResponseEntity.ok(updated)).defaultIfEmpty(ResponseEntity.notFound().build())
				.doOnError(e -> log.error("Failed to update course"));
	}

	@DeleteMapping("/users/{id}")
	public Mono<?> delete(@PathVariable("id") String id) {
		return userRepository.findById(id)
				.flatMap(user -> userRepository.deleteById(id).then(Mono.just(ResponseEntity.ok(user))))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

}
