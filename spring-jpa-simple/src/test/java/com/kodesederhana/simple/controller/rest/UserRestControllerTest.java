package com.kodesederhana.simple.controller.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.kodesederhana.simple.entity.User;
import com.kodesederhana.simple.repository.UserRepository;



@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRepository userRepository;

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		User user = userRepository.findOneByName("test001");
		
		this.mockMvc.perform(get("/api/users/" + user.getId())).andDo(print())
		.andExpect(status().isOk())
        .andExpect(jsonPath("$.id", Matchers.is(1)));
	}

}
