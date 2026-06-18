package com.irctc.userservice.controller;

import com.irctc.userservice.exception.ResourceNotFoundException;
import com.irctc.userservice.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ResetPasswordController {

    private final PasswordResetTokenService passwordResetTokenService;

    @GetMapping("/reset-password")
    public String showResetPasswordPage(
            @RequestParam String token,
            Model model) {

        try {

            passwordResetTokenService.validateToken(token);

            model.addAttribute("token", token);

            return "reset-password";

        }
        catch (ResourceNotFoundException ex) {

            return "invalid-token";

        }

    }

}
