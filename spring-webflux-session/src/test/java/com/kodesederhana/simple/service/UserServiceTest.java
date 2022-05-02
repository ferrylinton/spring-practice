package com.kodesederhana.simple.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kodesederhana.simple.entity.Role;
import com.kodesederhana.simple.entity.User;
import com.kodesederhana.simple.repository.RoleRepository;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private Pbkdf2PasswordService pbkdf2PasswordService;
	
	
	@Test
	public void testFindByUsername() {
		User user = userService.findByUsername("admin").block();
		assertEquals("admin", user.getUsername());
	}
	
//	@Test
//	public void testSave() {
//		List<Role> roles = roleRepository.findAll().collectList().block();
//		User user = new User();
//		user.setUsername("ferrylinton");
//		user.setEmail("ferrylinton@gmail.com");
//		user.setPassword(pbkdf2PasswordService.decode("password"));
//		user.setRoles(roles);
//		
//		user = userService.save(user).block();
//		assertEquals("ferrylinton", user.getUsername());
//	}
	
//	@Test
//	public void testUpdate() {
//		List<Role> roles = roleRepository.findAll().collectList().block();
//		
//		User user = userService.findByUsername("admin").block();
//		user.getRoles().clear();
//		user.getRoles().add(roles.get(0));
//		
//		user = userService.update(user).block();
//		assertEquals(1, user.getRoles().size());
//	}
	
}
