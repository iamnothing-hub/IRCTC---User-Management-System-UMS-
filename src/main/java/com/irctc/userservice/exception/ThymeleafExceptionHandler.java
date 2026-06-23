package com.irctc.userservice.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.irctc.userservice.controller.web")
public class ThymeleafExceptionHandler {

    @ExceptionHandler(InvalidTokenException.class)
    public String alreadyUsedExceptionHandler(InvalidTokenException ex){
        return "invalid-token";
    }

    @ExceptionHandler(TokenExpiredException.class)
    public String expiredTokenExceptionHandler(TokenExpiredException ex){
        return "token-expired";
    }
}
