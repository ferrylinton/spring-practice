package com.kodesederhana.simple.controller.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloRestControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void testHello() {
		webTestClient
			.get().uri("/hello")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
				.expectStatus().isOk()
				.expectBody(Map.class).value(entry -> {
					assertThat(entry.get("message")).isEqualTo("Salam perdamaian !!");
				});
	}

}
