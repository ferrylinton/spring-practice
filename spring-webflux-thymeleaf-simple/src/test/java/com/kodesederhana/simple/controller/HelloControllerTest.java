package com.kodesederhana.simple.controller;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerTest {


	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void testHello() {
		webTestClient
			.get().uri("/hello")
			.accept(MediaType.TEXT_HTML)
			.exchange()
				.expectStatus().isOk()
				.expectBody(String.class).value(Matchers.containsString("Salam perdamaian !!"));
	}

}
