package com.example.kafka.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kafka.model.User;
import com.example.kafka.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Automatically generates a constructor for the final fields below
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "auth-topic";

    @Transactional
    public void userRegistration(User user) { // Removed @RequestBody
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        String kafkaPayload = String.format("New user registered: %s", user.getEmail()); // camelCase
        kafkaTemplate.send(TOPIC, user.getEmail(), kafkaPayload);
    }

    public void userLogin(String email, String password){
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException(" Invalid Credentials");
        }

        String kafkaPayload = String.format("Login successful: %s", user.getEmail());
        kafkaTemplate.send(TOPIC, user.getEmail(), kafkaPayload);
}
}