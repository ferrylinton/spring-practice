package com.kodesederhana.simple.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AppException{

	private static final long serialVersionUID = 1L;

	public NotFoundException() {
		super("Data is not found", 404, HttpStatus.NOT_FOUND);
	}


	
}
