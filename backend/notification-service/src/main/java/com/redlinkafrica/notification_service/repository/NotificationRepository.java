package com.redlinkafrica.notification_service.repository;

import com.redlinkafrica.notification_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(String userId);
    List<Notification> findByStatus(Notification.Status status);
}