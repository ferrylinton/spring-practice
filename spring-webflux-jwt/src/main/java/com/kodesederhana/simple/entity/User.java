package com.kodesederhana.simple.entity;



import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(value = "m_user")
@Setter
@Getter
public class User{

	@Id
	private UUID id;

	private String username;

	private String email;
	
	@JsonIgnore
	private String password;
	
	@Transient
	private List<Role> roles;
	
}
