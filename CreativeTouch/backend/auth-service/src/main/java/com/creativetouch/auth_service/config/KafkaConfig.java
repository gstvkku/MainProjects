package com.creativetouch.auth_service.config;

import com.creativetouch.auth_service.event.UserRegisteredEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate(
            org.springframework.kafka.core.ProducerFactory<String, UserRegisteredEvent> producerFactory) {

        return new KafkaTemplate<>(producerFactory);
    }
}