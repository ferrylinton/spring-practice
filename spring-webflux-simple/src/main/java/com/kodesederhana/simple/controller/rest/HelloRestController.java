package com.kodesederhana.simple.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class HelloRestController {

	@GetMapping("/hello")
    public ResponseEntity<Mono<Map<String, String>>> hello() {
        Map<String, String> data = new HashMap<>();
        data.put("message", "Salam perdamaian !!");

        return ResponseEntity.ok(Mono.just(data));
    }
	
}
