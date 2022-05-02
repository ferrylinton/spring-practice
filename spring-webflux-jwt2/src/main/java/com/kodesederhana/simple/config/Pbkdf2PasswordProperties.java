package com.kodesederhana.simple.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "pbkdf2password")
public class Pbkdf2PasswordProperties {

	private String secret;

	private Integer iteration;

	private Integer keylength;

}
