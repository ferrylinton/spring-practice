package com.kodesederhana.simple.entity;



import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;


@Table(value = "m_role")
@Setter
@Getter
public class Role {

	@Id
	private UUID id;

	private String name;

}
