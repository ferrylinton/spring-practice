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

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserRestController {

	private final UserRepository userRepository;

	@GetMapping("/users")
	public ResponseEntity<?> findAll(Pageable pageable) {
		try {
			Page<?> page = userRepository.findAll(pageable);
			return new ResponseEntity<>(page, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") String id) {
		try {
			Optional<User> current = userRepository.findById(id);
			if (current.isPresent()) {
				return new ResponseEntity<>(current.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/users")
	public ResponseEntity<?> create(@RequestBody User user) {
		try {
			user = userRepository.save(user);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> update(@PathVariable("id") String id, @RequestBody User user) {
		try {
			Optional<User> current = userRepository.findById(id);
			if (current.isPresent()) {
				user.setId(current.get().getId());
				return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
		try {
			Optional<User> current = userRepository.findById(id);
			if (current.isPresent()) {
				userRepository.deleteById(id);
				return new ResponseEntity<>(null, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
