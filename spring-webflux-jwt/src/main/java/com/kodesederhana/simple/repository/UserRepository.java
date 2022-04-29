package com.kodesederhana.simple.repository;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.kodesederhana.simple.entity.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<User, UUID>{

	Mono<User> findOneByUsername(String username);
	
	Flux<User> findAllBy(Pageable pageable);
	
}
