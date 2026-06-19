package com.example.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.TimeZone;

@SpringBootApplication
public class KafkaApplication {

    public static void main(String[] args) {
        // Run this before SpringApplication.run starts
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        
        SpringApplication.run(KafkaApplication.class, args);
    }
}