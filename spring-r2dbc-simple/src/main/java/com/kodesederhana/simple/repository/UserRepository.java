package com.kodesederhana.simple.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.kodesederhana.simple.entity.User;

import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<User, Long>{

	Mono<User> findOneByName(String name);
	
}
