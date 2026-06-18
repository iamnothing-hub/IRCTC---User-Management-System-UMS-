package com.irctc.userservice.service.impl;

import com.irctc.userservice.entity.User;
import com.irctc.userservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Override
    public void sendMail(String to, String subject, String body) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);

        mailSender.send(simpleMailMessage);
    }

    @Override
    public void setPasswordSetupEmail(User user, String token) {

        String link = "http://localhost:8080/api-user/auth/reset-password?token=" + token;

        String body = """
                Welcome to IRCTC
                
                Username: %s
                
                Please click below link to set password:
                %s
                
                This link will expire in 24 hours.
                """.formatted(user.getUsername(), link);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(mailFrom);
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Set Your Password");
        simpleMailMessage.setText(body);

        mailSender.send(simpleMailMessage);

    }
}
