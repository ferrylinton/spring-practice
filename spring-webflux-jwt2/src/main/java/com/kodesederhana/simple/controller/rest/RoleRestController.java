package com.kodesederhana.simple.controller.rest;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodesederhana.simple.entity.Role;
import com.kodesederhana.simple.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/roles")
public class RoleRestController {

	private final RoleRepository roleRepository;

	@GetMapping
    Flux<Role> findAll() {
        return roleRepository.findAll();
    }
	
	@GetMapping("/{id}")
    Mono<Role> findById(@PathVariable("id") UUID id) {
        return roleRepository.findById(id);
    }

    @PostMapping
    Mono<Role> add(@RequestBody Role Role) {
        return roleRepository.save(Role);
    }

    @PutMapping
    Mono<Role> update(@RequestBody Role Role) {
        return roleRepository.save(Role);
    }

    @DeleteMapping("/{id}")
    Mono<Void> deleteById(@PathVariable("id") UUID id) {
        return roleRepository.deleteById(id);
    }

}
