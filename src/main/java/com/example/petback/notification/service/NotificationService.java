package com.example.petback.notification.service;

import com.example.petback.notification.dto.NotificationListResponseDto;
import com.example.petback.notification.entity.Notification;
import com.example.petback.user.entity.User;

public interface NotificationService {
    NotificationListResponseDto selectNotifications(User user);

    void deleteNotification(Long notificationId, User user);

    Notification findNotification(Long notificationId);

    void readNotification(Long notificationId, User user);
}
