package com.kodesederhana.simplemongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kodesederhana.simplemongodb.model.User;

public interface UserRepository extends MongoRepository<User, String> {

}
