package org.example.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventConsumer {
    private final EmailService emailService;

    @Autowired
    public UserEventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${kafka.topic.user-events}", groupId = "notification-group")
    public void handleUserEvent(UserEvent userEvent) {
        if (userEvent.getEventType() == UserEvent.EventType.CREATED) {
            emailService.sendAccountCreatedEmail(userEvent.getEmail());
        } else if (userEvent.getEventType() == UserEvent.EventType.DELETED) {
            emailService.sendAccountDeletedEmail(userEvent.getEmail());
        }
    }
}
