package com.irctc.userservice.service;

import com.irctc.userservice.entity.PasswordResetToken;
import com.irctc.userservice.entity.User;

public interface PasswordResetTokenService {

    PasswordResetToken createToken(User user);

    PasswordResetToken validateToken(String token);

    void markAsUsed(PasswordResetToken token);
}
