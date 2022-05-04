package com.kodesederhana.simple.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponseDto {

	private String token;
	
	private String username;
	
	private List<String> authorities;
	
}
