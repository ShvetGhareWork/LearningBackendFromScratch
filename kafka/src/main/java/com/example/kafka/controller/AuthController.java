package com.example.kafka.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafka.model.User;
import com.example.kafka.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/signup")
public ResponseEntity<String> signup(@RequestBody User user) {
    try {
        authService.userRegistration(user);
        return ResponseEntity.ok("User registered successfully");
    } catch (RuntimeException e) {
        // Return 409 Conflict with the specific message "Email already exists"
        return ResponseEntity.status(409).body(e.getMessage());
    } catch (Exception e) {
        // Fallback for other unexpected database/Kafka errors
        return ResponseEntity.status(500).body("An unexpected error occurred");
    }
}


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try{
        authService.userLogin(user.getEmail(), user.getPassword());
        return ResponseEntity.ok("Login successful!");
        } catch (Exception e){
            return ResponseEntity.status(401).body("Login Failed" + e.getMessage());
        }
    }
}
