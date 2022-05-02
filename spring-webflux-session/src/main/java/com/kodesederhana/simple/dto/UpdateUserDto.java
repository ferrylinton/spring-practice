package com.kodesederhana.simple.dto;



import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UpdateUserDto{

	@NotNull(message = "id can not be empty")
	private UUID id;

	@NotNull(message = "username can not be empty")
	private String username;

	@NotNull(message = "email can not be empty")
	private String email;

	@NotNull(message = "roles can not be empty")
	private Set<String> roles;
	
}
