package com.irctc.userservice.service;

import com.irctc.userservice.dto.request.CreateUserRequest;
import com.irctc.userservice.dto.request.ResetPasswordRequest;
import com.irctc.userservice.dto.response.CreateUserResponse;

public interface UserService {

    CreateUserResponse createUser(CreateUserRequest createUserRequest);

    void setPassword(ResetPasswordRequest request);
}
