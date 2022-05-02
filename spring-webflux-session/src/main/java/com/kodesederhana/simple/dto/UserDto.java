package com.kodesederhana.simple.dto;



import java.util.List;
import java.util.UUID;

import com.kodesederhana.simple.entity.Role;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UserDto{

	private UUID id;

	private String username;

	private String email;

	private List<Role> roles;
	
}
