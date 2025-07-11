package org.example.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String websiteUrl;

    public EmailService(JavaMailSender mailSender,
                        @Value("${app.website-url}") String websiteUrl) {
        this.mailSender = mailSender;
        this.websiteUrl = websiteUrl;
    }

    public void sendAccountCreatedEmail(String email) {
        String subject = "Добро пожаловать!";
        String message = String.format("Здравствуйте! Ваш аккаунт на сайте %s был успешно создан.", websiteUrl);
        sendEmail(email, subject, message);
    }

    public void sendAccountDeletedEmail(String email) {
        String subject = "Ваш аккаунт был удалён";
        String message = "Здравствуйте! Ваш аккаунт был удалён.";
        sendEmail(email, subject, message);
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
