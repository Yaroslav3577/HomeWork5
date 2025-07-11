package org.example.notification;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class UserEventHandlerUnitTest {
    @Test
    void shouldCallEmailServiceOnEvent() {
        EmailService emailService = Mockito.mock(EmailService.class);

        UserEventHandler handler = new UserEventHandler(emailService);

        UserEvent event = new UserEvent("test@example.com", UserEvent.EventType.CREATED);

        handler.handleUserEvent(event);

        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        verify(emailService).sendEmail(emailCaptor.capture(), any(), any());

        assertEquals("test@example.com", emailCaptor.getValue());
    }
}