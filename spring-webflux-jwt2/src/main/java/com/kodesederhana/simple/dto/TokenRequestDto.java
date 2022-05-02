package com.kodesederhana.simple.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenRequestDto {

	@NotNull(message = "username can not be empty")
	private String username;

	@NotNull(message = "password can not be empty")
	private String password;

}
