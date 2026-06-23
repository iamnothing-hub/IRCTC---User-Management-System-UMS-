package com.irctc.userservice.controller;

import com.irctc.userservice.dto.request.ResetPasswordRequest;
import com.irctc.userservice.exception.ResourceNotFoundException;
import com.irctc.userservice.service.PasswordResetTokenService;
import com.irctc.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class ResetPasswordController {

    private final PasswordResetTokenService passwordResetTokenService;
    private final UserService userService;

    @GetMapping("/reset-password")
    public String showResetPasswordPage(
            @RequestParam String token,
            Model model) {

        passwordResetTokenService.validateToken(token);

        ResetPasswordRequest request =
                new ResetPasswordRequest();

        request.setToken(token);

        model.addAttribute(
                "resetPasswordRequest",
                request);

        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @Valid @ModelAttribute("resetPasswordRequest") ResetPasswordRequest resetPasswordRequest,
            BindingResult bindingResult
    ){
        log.info("reset password reques: {}", resetPasswordRequest);
        log.info("binding result: {}", bindingResult);
        if(bindingResult.hasErrors()){
            return "reset-password";
        }
        userService.setPassword(resetPasswordRequest);
        return "success";
    }

}
