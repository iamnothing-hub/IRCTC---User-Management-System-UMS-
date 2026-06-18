package com.irctc.userservice.service;

import com.irctc.userservice.entity.User;

public interface EmailService {

    void sendMail(String to, String subject, String body);  // We will not use this because we need to add body again and again

    void setPasswordSetupEmail(User user, String token);
}
