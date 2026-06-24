package com.example.notification_service.consumer;

import com.example.notification_service.dto.UserRegisteredEvent;
import com.example.notification_service.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaNotificationConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "auth-topic", groupId = "notification-group")
    public void consumeUserRegistration(UserRegisteredEvent event) {
        System.out.println("Received registration event from Kafka for email: " + event.getEmail());
        try {
            emailService.sendWelcomeEmail(event.getEmail(), event.getUsername());
        } catch (Exception e) {
            System.err.println("Error processing registration event: " + e.getMessage());
        }
    }
}
