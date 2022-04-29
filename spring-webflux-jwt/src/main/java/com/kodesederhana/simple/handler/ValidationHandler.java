package com.kodesederhana.simple.handler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
public class ValidationHandler {

	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<?> handle(WebExchangeBindException ex, ServerHttpRequest request) {
		
		StringBuilder builder = new StringBuilder("Required parameter [");
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			if(builder.toString().contains("'")) {
				builder.append(",");
			}
			
			String field = ((FieldError) error).getField();
			builder.append("'").append(field).append("'");
		});
		
		builder.append("] is not present");
		
		
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("timestamp", new Date());
		data.put("status", 400);
		data.put("error", "Bad Request");
		data.put("message", builder.toString());
		data.put("path", request.getURI());
		
		return new ResponseEntity<Map<String, Object>>(data, HttpStatus.BAD_REQUEST);
	}

}

