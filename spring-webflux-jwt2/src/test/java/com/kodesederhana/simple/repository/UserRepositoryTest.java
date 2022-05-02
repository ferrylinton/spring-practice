package com.kodesederhana.simple.repository;

import java.util.Objects;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kodesederhana.simple.entity.User;

import reactor.test.StepVerifier;

@SpringBootTest
public class UserRepositoryTest {

//	@Autowired
//	private UserRepository userRepository;
//	
//	@BeforeAll
//	void setUp() {
//		
//	}
//	
//	@Test
//	void findByIdTest() {
//		User user = new User();
//	    user.setName("ferry");
//	    user.setEmail("ferrylinton@gmail.com");
//	    user = userRepository.save(user).block();
//	    
//	    userRepository.findById(user.getId())
//	        .as(StepVerifier::create)
//	        .expectNextMatches(equalTo(user))
//	        .verifyComplete();
//	}
//	
//	@Test
//	void whenFindingAnUserReturnTheUserIfExist() {
//	    User user = new User();
//	    user.setName("ferry");
//	    user.setEmail("ferrylinton@gmail.com");
//	    userRepository.save(user)
//	    	.map(newUser -> {
//	    		System.out.println(newUser.getId());
//	    		return newUser;
//	    	})
//	    	.subscribe();
//	    StepVerifier.create(userRepository.findOneByName(user.getName())).expectNextMatches(userCreated -> userCreated.getName().equals(user.getName())).verifyComplete();
//	}
//	
//	Predicate<User> equalTo(User expected) {
//	    return actual -> Objects.equals(actual.getId(), expected.getId())
//	        && Objects.equals(actual.getEmail(), expected.getEmail());
//	  }
	
}
