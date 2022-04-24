package com.kodesederhana.simple.controller.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloRestControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;

	@SuppressWarnings("unchecked")
	@Test
	public void testHello() throws JsonMappingException, JsonProcessingException {
		String response = this.restTemplate.getForObject("http://localhost:" + port + "/hello", String.class);
		Map<String, String> map = objectMapper.readValue(response, Map.class);
		
		assertThat(map).containsEntry("message", "Salam perdamaian !!");
	}

}
