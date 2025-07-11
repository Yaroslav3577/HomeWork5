package org.example.notification;

public class UserEvent {
    private String email;
    private EventType eventType;
    public UserEvent(String email, EventType eventType){
        this.email = email;
        this.eventType = eventType;
    }

    public String toMessageString() {
        return email + ":" + eventType.name();
    }

    public static UserEvent fromMessageString(String message) {
        String[] parts = message.split(":");
        return new UserEvent(parts[0], EventType.valueOf(parts[1]));
    }

    public enum EventType {
        CREATED, DELETED
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "email='" + email + '\'' +
                ", eventType=" + eventType +
                '}';
    }
}
