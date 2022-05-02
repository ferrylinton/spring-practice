package com.kodesederhana.simple.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class AppException extends Exception{

	private static final long serialVersionUID = 1L;
	
	@Getter
	private int code;
	
	@Getter
	private HttpStatus httpStatus;
	
	public AppException(String message, int code) {
		super(message);
		this.code = code;
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	
	public AppException(String message, int code, HttpStatus httpStatus) {
		super(message);
		this.code = code;
		this.httpStatus = httpStatus;
	}

}

