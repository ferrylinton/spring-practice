package com.kodesederhana.simple.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRestController {

	@GetMapping("/hello")
    public Map<String, String> hello() {
        Map<String, String> data = new HashMap<>();
        data.put("message", "Salam perdamaian !!");

        return data;
    }
	
}
