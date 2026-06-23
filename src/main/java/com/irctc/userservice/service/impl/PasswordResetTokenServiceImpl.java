package com.irctc.userservice.service.impl;

import com.irctc.userservice.entity.PasswordResetToken;
import com.irctc.userservice.entity.User;
import com.irctc.userservice.exception.InvalidTokenException;
import com.irctc.userservice.exception.ResourceNotFoundException;
import com.irctc.userservice.exception.TokenExpiredException;
import com.irctc.userservice.repository.PasswordResetTokenRepository;
import com.irctc.userservice.repository.UserRepository;
import com.irctc.userservice.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final UserRepository userRepository;

    @Override
    public PasswordResetToken createToken(User user) {

        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .token(UUID.randomUUID().toString())
                .used(false)
                .user(user)
                .expiryDate(Instant.now().plus(24, ChronoUnit.HOURS))
                .build();
        PasswordResetToken generatedToken = passwordResetTokenRepository.save(passwordResetToken);

        return generatedToken;
    }

    @Override
    public PasswordResetToken validateToken(String token) {

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("Invalid Token."));

        if(passwordResetToken.getUsed()){
            throw new InvalidTokenException("Token is already used");
        }

        if(passwordResetToken.getExpiryDate().isBefore(Instant.now())){
            throw new TokenExpiredException("Token is expired.");
        }

        return passwordResetToken;
    }

    @Override
    public void markAsUsed(PasswordResetToken token) {
        token.setUsed(true);
        passwordResetTokenRepository.save(token);
    }
}
