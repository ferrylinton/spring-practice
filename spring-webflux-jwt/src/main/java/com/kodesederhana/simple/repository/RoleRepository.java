package com.kodesederhana.simple.repository;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import com.kodesederhana.simple.entity.Role;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleRepository extends R2dbcRepository<Role, UUID>{

	Mono<Role> findOneByName(String name);
	
	Flux<Role> findByNameIn(Collection<String> names);
	
}
