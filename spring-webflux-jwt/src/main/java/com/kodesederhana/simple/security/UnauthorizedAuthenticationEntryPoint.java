package com.kodesederhana.simple.security;

import java.nio.charset.StandardCharsets;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UnauthorizedAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

	private static final String body = "{\"status\":401,\"error\":\"Unauthorized\"}";

	@Override
	public Mono<Void> commence(final ServerWebExchange exchange, final AuthenticationException e) {
		return Mono.fromRunnable(() -> {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
			DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
			exchange.getResponse().writeWith(Flux.just(buffer));
		});
	}

}
