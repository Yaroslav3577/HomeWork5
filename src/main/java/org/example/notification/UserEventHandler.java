package org.example.notification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventHandler {
    private final EmailService emailService;
    public UserEventHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "user-events")
    public void handleUserEvent(UserEvent event) {
        String subject = "Уведомление о вашем аккаунте";
        String text = event.getEventType() == UserEvent.EventType.CREATED
                ? "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан."
                : "Здравствуйте! Ваш аккаунт был удалён.";

        emailService.sendEmail(event.getEmail(), subject, text);
    }
}
