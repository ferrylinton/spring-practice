package com.kodesederhana.simple.controller.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping
    public Mono<Page<User>> getAll(
    		@RequestParam(name = "page", defaultValue = "0") int page, 
    		@RequestParam(name = "size", defaultValue = "10") int size){
		
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("name"));
		
        return userRepository
        		.findAllBy(PageRequest.of(page, size))
        		.collectList()
                .zipWith(userRepository.count())
                .map(t -> new PageImpl<>(t.getT1(), pageRequest, t.getT2()));
    }

	
	@GetMapping("/{id}")
	public ResponseEntity<Mono<User>> getByUsername(@PathVariable(value = "id") Long id) {
		Mono<User> user = userRepository.findById(id);

		return ResponseEntity.ok().body(user);
	}

}
