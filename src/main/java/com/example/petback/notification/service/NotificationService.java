package com.example.petback.notification.service;

import com.example.petback.notification.dto.NotificationResponseDto;
import com.example.petback.notification.entity.Notification;
import com.example.petback.user.entity.User;

import java.util.List;

public interface NotificationService {
    List<NotificationResponseDto> selectNotifications(User user);

    void deleteNotification(Long notificationId, User user);

    Notification findNotification(Long notificationId);

}
