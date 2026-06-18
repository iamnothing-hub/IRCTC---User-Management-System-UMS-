package com.irctc.userservice.service.impl;

import com.irctc.userservice.service.PasswordGeneratorService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class PasswordGeneratorServiceImpl implements PasswordGeneratorService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz"
            + "0123456789"
            + "!@#$%^&*.";

    private static final int PASSWORD_LENGHT = 12;

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String generatePassword() {
        StringBuilder password = new StringBuilder();

        for(int i=0; i<PASSWORD_LENGHT; i++){
            int index = secureRandom.nextInt(CHARACTERS.length());

            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }
}
