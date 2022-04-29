package com.kodesederhana.simple.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Pbkdf2PasswordServiceTest {

	@Autowired
	private Pbkdf2PasswordService pbkdf2PasswordService;
	
	@Test
	public void testEncodeAndDecode() {
		String plainText = "password";
		String encryptedText = pbkdf2PasswordService.encode(plainText);
		System.out.println(encryptedText);
		assertEquals(plainText, pbkdf2PasswordService.decode(encryptedText));
	}
	
}
