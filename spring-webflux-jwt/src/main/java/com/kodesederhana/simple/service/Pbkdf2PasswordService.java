package com.kodesederhana.simple.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.kodesederhana.simple.config.Pbkdf2PasswordProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Pbkdf2PasswordService {

	private String salt = "salt";

	private static final byte[] ivBytes = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
	
	private final Decoder decoder = Base64.getDecoder();
	
	private final Encoder encoder = Base64.getEncoder();

	private SecretKeySpec secretKeySpec;

	private Cipher encodeCipher;
	
	private Cipher decodeCipher;
	
	private final Pbkdf2PasswordProperties pbkdf2PasswordProperties;
	
	public Pbkdf2PasswordService(Pbkdf2PasswordProperties pbkdf2PasswordProperties) {
		this.pbkdf2PasswordProperties = pbkdf2PasswordProperties;
	}
	
	public String encode(String plainText) {
		try {
			byte[] encrypted = encodeCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
			return encoder.encodeToString(encrypted);
		} catch (Exception ex1) {
			log.error("encode try 1 : {}, error : {}", plainText, ex1.getLocalizedMessage());
			sleep(5);
			
			try {
				initEncodeCipher();
				byte[] encrypted = encodeCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
				return encoder.encodeToString(encrypted);
			} catch (Exception ex2) {
				log.error("encode try 2 : {}, error : {}", plainText, ex1.getLocalizedMessage());
				throw new RuntimeException(ex2);
			}
		}
	}

	public String decode(String encryptedText) {
		try {
			byte[] decrypted = decodeCipher.doFinal(decoder.decode(encryptedText));
			return new String(decrypted, StandardCharsets.UTF_8);
		} catch (Exception ex1) {
			log.error("decode try 1 : {}, error : {}", encryptedText, ex1.getLocalizedMessage());
			sleep(5);
			
			try {
				initDecodeCipher();
				byte[] decrypted = decodeCipher.doFinal(decoder.decode(encryptedText));
				return new String(decrypted, StandardCharsets.UTF_8);
			} catch (Exception ex2) {
				log.error("decode try 2 : {}, error : {}", encryptedText, ex2.getLocalizedMessage());
				return String.valueOf(System.currentTimeMillis());
			}
		}
	}

	@PostConstruct
	private void init() throws Exception {
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		PBEKeySpec pbeKeySpec = new PBEKeySpec(pbkdf2PasswordProperties.getSecret().toCharArray(), salt.getBytes(), pbkdf2PasswordProperties.getIteration(), pbkdf2PasswordProperties.getKeylength());
		SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);

		secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
		
		initEncodeCipher();
		initDecodeCipher();
	}
	
	private void initEncodeCipher() throws Exception {
		encodeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		encodeCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));
	}
	
	private void initDecodeCipher() throws Exception {
		decodeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		decodeCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));
	}
	
	private void sleep (int val) {
	    try { 
	        TimeUnit.SECONDS.sleep(val);
	    } catch (InterruptedException e) {
	        log.error("Thread interrupted");
	    }
	}

}
