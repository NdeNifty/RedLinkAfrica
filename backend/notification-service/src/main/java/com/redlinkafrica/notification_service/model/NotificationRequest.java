package com.redlinkafrica.notification_service.model;

import com.redlinkafrica.notification_service.model.Notification.Channel;

public class NotificationRequest {
    private String userId;
    private String message;
    private Channel channel;

    // Default constructor (required for Jackson deserialization)
    public NotificationRequest() {}

    // Parameterized constructor
    public NotificationRequest(String userId, String message, Channel channel) {
        this.userId = userId;
        this.message = message;
        this.channel = channel;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}