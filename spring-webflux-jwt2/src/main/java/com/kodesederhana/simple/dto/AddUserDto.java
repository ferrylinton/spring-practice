package com.kodesederhana.simple.dto;



import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class AddUserDto{

	@NotNull(message = "username can not be empty")
	private String username;

	@NotNull(message = "email can not be empty")
	private String email;
	
	@NotNull(message = "password can not be empty")
	private String password;

	@NotNull(message = "roles can not be empty")
	private Set<String> roles;
	
}
