package com.example.petback.notification.service;

import com.example.petback.notification.dto.NotificationResponseDto;
import com.example.petback.notification.entity.Notification;
import com.example.petback.notification.repository.NotificationRepository;
import com.example.petback.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDto> selectNotifications(User user) {
        return notificationRepository.findByReservation_User(user).stream().map(NotificationResponseDto::of).toList();
    }

    @Override
    @Transactional
    public void deleteNotification(Long notificationId, User user) {
        Notification notification = findNotification(notificationId);
        notificationRepository.delete(notification);
    }

    @Override
    public Notification findNotification(Long id) {
        return notificationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("알림이 존재하지 않습니다.")
        );
    }
}
