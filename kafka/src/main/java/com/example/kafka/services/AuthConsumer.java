package com.example.kafka.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AuthConsumer {

    @KafkaListener(topics = "auth-topic", groupId = "auth-group")
    public void consume(String message) {
        System.out.println("====== KAFKA RECEIVED ======");
        System.out.println(message);
        System.out.println("=============================");
    }
}