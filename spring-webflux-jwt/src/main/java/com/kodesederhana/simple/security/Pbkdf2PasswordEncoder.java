package com.kodesederhana.simple.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kodesederhana.simple.service.Pbkdf2PasswordService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Pbkdf2PasswordEncoder implements PasswordEncoder {

    private final Pbkdf2PasswordService pbkdf2PasswordService;


    @Override
    public String encode(CharSequence cs) {
       return pbkdf2PasswordService.encode(cs.toString());
    }

    @Override
    public boolean matches(CharSequence cs, String string) {
        return encode(cs).equals(string);
    }
}
