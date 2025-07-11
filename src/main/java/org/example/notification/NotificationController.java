package org.example.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final EmailService emailService;

    @Autowired
    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/account-created")
    public void sendAccountCreatedNotification(@RequestBody String email) {
        emailService.sendAccountCreatedEmail(email);
    }

    @PostMapping("/account-deleted")
    public void sendAccountDeletedNotification(@RequestBody String email) {
        emailService.sendAccountDeletedEmail(email);
    }
}
