package com.example.kafka.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kafka.dto.AuthenticationResponse;
import com.example.kafka.model.User;
import com.example.kafka.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Automatically generates a constructor for the final fields below
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final JwtService jwtService;

    private static final String TOPIC = "auth-topic";


    // Why AuthResponse and not just void.
    // Because initial only login and authentication was carried out by matching the email, password.
    // Now, a String or Token needs to be provided to the user so the user need to recieve a Token as a response or return type should be that token.
    // For this purpose, A DTO was created named AuthenticationResponse that returns the Token.
    @Transactional
    public AuthenticationResponse userRegistration(User user) { // Removed @RequestBody
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        // 1. User Regstration
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        // 2. Kafka Payload Setup
        String kafkaPayload = String.format("New user registered: %s", user.getEmail()); // camelCase
        kafkaTemplate.send(TOPIC, user.getEmail(), kafkaPayload);

        // 3. Generate and return JWT.
        String jwtToken = jwtService.generateToken(user.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse userLogin(String email, String password){
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException(" Invalid Credentials");
        }

        // Login successful and kafka Payload
        String kafkaPayload = String.format("Login successful: %s", user.getEmail());
        kafkaTemplate.send(TOPIC, user.getEmail(), kafkaPayload);

        // 2. Generate and return the JWT
        String jwtToken = jwtService.generateToken(user.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
}
}