package com.irctc.userservice.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserResponse {
    private Long userId;

    private String username;

    private String message;
}
