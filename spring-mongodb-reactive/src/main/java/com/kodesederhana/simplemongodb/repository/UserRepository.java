package com.kodesederhana.simplemongodb.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.kodesederhana.simplemongodb.model.User;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
