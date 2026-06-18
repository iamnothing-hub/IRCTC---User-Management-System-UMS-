package com.irctc.userservice.service.impl;

import com.irctc.userservice.repository.UserRepository;
import com.irctc.userservice.service.UsernameGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsernameGeneratorServiceImpl implements UsernameGeneratorService {

    private final UserRepository userRepository;

    @Override
    public String generateUsername(String firstName, String lastName) {

        String baseUsername = firstName.toLowerCase() +"." + lastName.toLowerCase();

        String username = baseUsername;

        int counter = 1;

        while(userRepository.existsByUsername(username)){
            username = baseUsername + counter;
            counter++;
        }
        return username;
    }
}
