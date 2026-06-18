package com.irctc.userservice.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private LocalDateTime localDateTime;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
