package com.kodesederhana.simple.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodesederhana.simple.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findOneByName(String name);
	
}
