package com.kodesederhana.simple.security;

import java.io.UnsupportedEncodingException;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;

@Component
public class LoginFailedHandler implements ServerAuthenticationFailureHandler {
    
	@Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException e) {
        
        Mono<Void> ret = null;
        try {
        	JSONObject params = new JSONObject();
            params.put("code", 400);
            params.put ("MSG", "login failed!");

            ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            ret = response.writeAndFlushWith(Flux.just(ByteBufFlux.just(response.bufferFactory().wrap(params.toString().getBytes("UTF-8")))));
        } catch (UnsupportedEncodingException | JSONException e0) {
            e0.printStackTrace();
        }
        
        return ret;
    }
}
