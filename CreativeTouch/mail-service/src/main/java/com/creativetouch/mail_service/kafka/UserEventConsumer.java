package com.creativetouch.mail_service.kafka;

import com.creativetouch.mail_service.event.UserRegisteredEvent;
import com.creativetouch.mail_service.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class UserEventConsumer {

    private final ObjectMapper objectMapper;
    private final MailService mailService;

    @KafkaListener(
            topics = "${app.kafka.topic.user-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(String message) {

        try {

            UserRegisteredEvent event =
                    objectMapper.readValue(
                            message,
                            UserRegisteredEvent.class
                    );

            mailService.sendWelcomeEmail(
                    event.email(),
                    event.name()
            );

        } catch (Exception e) {

            System.err.println(
                    "Erro ao processar evento de usuário: "
                            + e.getMessage()
            );
        }
    }
}