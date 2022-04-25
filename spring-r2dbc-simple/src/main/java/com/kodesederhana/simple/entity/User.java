package com.kodesederhana.simple.entity;



import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;


@Table(value = "m_user")
@Setter
@Getter
public class User {

	@Id
	private Long id;

	private String name;

	private String email;
}
