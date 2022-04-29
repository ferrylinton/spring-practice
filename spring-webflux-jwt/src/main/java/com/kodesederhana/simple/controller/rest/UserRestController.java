package com.kodesederhana.simple.controller.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodesederhana.simple.dto.AddUserDto;
import com.kodesederhana.simple.dto.UpdateUserDto;
import com.kodesederhana.simple.entity.Role;
import com.kodesederhana.simple.entity.User;
import com.kodesederhana.simple.repository.UserRepository;
import com.kodesederhana.simple.service.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserRestController {

	private final UserService userService;
	
	@GetMapping
	ResponseEntity<Flux<?>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }
	
	@GetMapping("/{id}")
	ResponseEntity<Mono<?>> findById(@PathVariable("id") UUID id) {
		return ResponseEntity.ok(userService.findById(id));
    }

	@PostMapping
	ResponseEntity<Mono<?>> add(@Valid @RequestBody AddUserDto dto) {
		return ResponseEntity.ok(userService.add(dto)); 
    }
	
	@PutMapping
	ResponseEntity<Mono<?>> update(@Valid @RequestBody UpdateUserDto dto) {
		return ResponseEntity.ok(userService.update(dto)); 
    }
	
	@DeleteMapping("/{id}")
	ResponseEntity<Mono<?>> deleteById(@PathVariable("id") UUID id) {
		return ResponseEntity.ok(userService.delete(id)); 
    }
	
//	@GetMapping
//    public Mono<Page<User>> getAll(
//    		@RequestParam(name = "page", defaultValue = "0") int page, 
//    		@RequestParam(name = "size", defaultValue = "10") int size){
//		
//		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("name"));
//		
//        return userRepository
//        		.findAllBy(PageRequest.of(page, size))
//        		.collectList()
//                .zipWith(userRepository.count())
//                .map(t -> new PageImpl<>(t.getT1(), pageRequest, t.getT2()));
//    }
//
//	
//	@GetMapping("/{id}")
//	public ResponseEntity<Mono<User>> getByUsername(@PathVariable(value = "id") UUID id) {
//		Mono<User> user = userRepository.findById(id);
//
//		return ResponseEntity.ok().body(user);
//	}

}
