package com.redlinkafrica.notification_service.controller;

import com.redlinkafrica.notification_service.model.Notification;
import com.redlinkafrica.notification_service.model.NotificationRequest;
import com.redlinkafrica.notification_service.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String DONOR_SERVICE_URL = "http://localhost:8080/api/donors/{id}";

    // POST /api/notifications/send - Send a notification
    @PostMapping("/send")
    public ResponseEntity<Notification> sendNotification(@RequestBody NotificationRequest request) {
        Notification notification = new Notification();
        notification.setUserId(request.getUserId());
        notification.setMessage(request.getMessage());
        notification.setChannel(request.getChannel());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setStatus(Notification.Status.PENDING);

        // Simulate sending notification
        try {
            // Placeholder: Simulate delay and success
            Thread.sleep(1000); // Simulate network delay
            notification.setSentAt(LocalDateTime.now());
            notification.setStatus(Notification.Status.SENT);
        } catch (InterruptedException e) {
            notification.setStatus(Notification.Status.FAILED);
        }

        Notification savedNotification = notificationRepository.save(notification);
        return ResponseEntity.ok(savedNotification);
    }

    // GET /api/notifications/history - Get notification history
    @GetMapping("/history")
    public List<Notification> getNotificationHistory() {
        return notificationRepository.findAll();
    }

    // GET /api/notifications/history/{recipient} - Get notifications by recipient
    @GetMapping("/history/{recipient}")
    public ResponseEntity<List<Notification>> getNotificationsByRecipient(@PathVariable String recipient) {
        List<Notification> notifications = notificationRepository.findByRecipient(recipient);
        return notifications.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(notifications);
    }
}