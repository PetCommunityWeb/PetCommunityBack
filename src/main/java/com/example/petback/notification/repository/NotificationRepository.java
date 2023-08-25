package com.example.petback.notification.repository;

import com.example.petback.notification.entity.Notification;
import com.example.petback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReservation_User(User user);
}
