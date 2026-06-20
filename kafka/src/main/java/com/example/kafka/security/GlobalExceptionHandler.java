package com.example.kafka.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.kafka.dto.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles incorrect passwords
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException ex) {
        ApiError error = new ApiError(
                HttpStatus.UNAUTHORIZED.value(), 
                "Invalid email or password", 
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    // Handles custom runtime exceptions (e.g., from your AuthService)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(), 
                ex.getMessage(), 
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Catch-all for anything unexpected
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                "An unexpected error occurred", 
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}