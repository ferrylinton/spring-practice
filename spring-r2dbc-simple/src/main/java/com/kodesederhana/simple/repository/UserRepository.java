package com.kodesederhana.simple.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.kodesederhana.simple.entity.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<User, Long>{

	Mono<User> findOneByName(String name);
	
	Flux<User> findAllBy(Pageable pageable);
	
}
