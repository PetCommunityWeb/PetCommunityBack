package com.example.petback.notification.dto;

import com.example.petback.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class NotificationListResponseDto {
    private List<NotificationResponseDto> notificationResponseDtos;
}
