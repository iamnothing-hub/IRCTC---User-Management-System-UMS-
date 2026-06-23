package com.irctc.userservice.controller.api;

import com.irctc.userservice.dto.request.CreateUserRequest;
import com.irctc.userservice.dto.request.ResetPasswordRequest;
import com.irctc.userservice.dto.response.CreateUserResponse;
import com.irctc.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest){
        CreateUserResponse user = userService.createUser(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(user);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String>
    resetPassword(
            @Valid
            @RequestBody
            ResetPasswordRequest request) {

        userService.setPassword(request);

        return ResponseEntity.ok(
                "Password reset successfully");
    }
}
