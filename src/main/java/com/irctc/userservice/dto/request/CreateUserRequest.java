package com.irctc.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    @NotBlank(message = "First name should not be empty.")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Email should not be empty.")
    @Email
    private String email;

//    @NotBlank(message = "Role should not be empty.")   => We can not use @NotBlank in Set data types
    @NotEmpty(message = "At least one role is required.")
    private Set<String> roles;
}
