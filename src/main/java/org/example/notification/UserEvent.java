package org.example.notification;

import org.example.User;

public class UserEvent {
    private User user;
    private EventType eventType;
    public UserEvent(User user , EventType eventType){
        this.user = user;
        this.eventType = eventType;
    }

    public enum EventType {
        CREATED, DELETED, UPDATED
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                "email='" + user.getMail() + '\'' +
                ", eventType=" + eventType +
                '}';
    }
}
