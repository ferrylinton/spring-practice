package com.kodesederhana.simple.session;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.ReactiveMapSessionRepository;
import org.springframework.session.ReactiveSessionRepository;
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

@Configuration
public class SessionConfig {
 
//    @Bean
//    public ReactiveSessionRepository<?> reactiveSessionRepository() {
//        return new ReactiveMapSessionRepository(new ConcurrentHashMap<>());
//    }
}
