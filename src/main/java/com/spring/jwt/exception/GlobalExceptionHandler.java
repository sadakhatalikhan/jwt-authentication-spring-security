package com.spring.jwt.exception;

import com.spring.jwt.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<APIResponse> handleDuplicateUsernameException(DuplicateUsernameException ex) {
        return ResponseEntity.ok(
                APIResponse.builder()
                        .errorCode(HttpStatus.BAD_REQUEST.value())
                        .errorMessage(HttpStatus.BAD_REQUEST.name())
                        .data(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<APIResponse> handleUsernameNotFoundException(LoginFailedException ex) {
        return ResponseEntity.ok(
                APIResponse.builder()
                        .errorCode(HttpStatus.BAD_REQUEST.value())
                        .errorMessage(HttpStatus.BAD_REQUEST.name())
                        .data(ex.getMessage())
                        .build()
        );
    }
}
