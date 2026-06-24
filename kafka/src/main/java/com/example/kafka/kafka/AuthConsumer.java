package com.example.kafka.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.kafka.services.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "auth-topic", groupId = "auth-group")
    public void consume(String message) {
        System.out.println("====== KAFKA RECEIVED ======");
        System.out.println(message);
        System.out.println("=============================");
    }

    @KafkaListener(topics = "auth-topic", groupId = "email-group")
    public void consumeEmail(String message) {
        System.out.println("Received Email: " + message);

        if (message.startsWith("New user registered: ")) {
            String email = message.replace("New user registered: ", "").trim();
            try {
                emailService.sendWelcomeEmail(email);
            } catch (Exception e) {
                System.err.println("Failed to send email to " + email + ": " + e.getMessage());
            }
        }
    }
}