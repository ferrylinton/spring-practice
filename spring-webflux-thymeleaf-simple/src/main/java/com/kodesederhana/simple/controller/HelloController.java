package com.kodesederhana.simple.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import reactor.core.publisher.Mono;

@Controller
public class HelloController {

	@GetMapping("/hello")
	public Mono<String> hello(Model model) {
		model.addAttribute("message", "Salam perdamaian !!");
		return Mono.just("hello");
	}

}
