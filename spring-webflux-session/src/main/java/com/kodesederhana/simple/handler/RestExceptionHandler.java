package com.kodesederhana.simple.handler;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kodesederhana.simple.exception.AppException;


@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(value = { AppException.class })
	public <E extends AppException> ResponseEntity<?> handle(E ex) {
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("code", ex.getCode());
		data.put("message", ex.getMessage());

		return new ResponseEntity<Map<?, ?>>(data, ex.getHttpStatus());
	}
	
	@ExceptionHandler(value = BadCredentialsException.class)
	public ResponseEntity<?> handle(BadCredentialsException ex) {
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("code", 401);
		data.put("message", ex.getMessage());

		return new ResponseEntity<Map<?, ?>>(data, HttpStatus.UNAUTHORIZED);
	}

}

