package com.example.petback.notification.dto;

import com.example.petback.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class NotificationResponseDto {
    private Long id;
    private String username;
    private String hospitalName;
    private LocalDate date;
    private LocalTime startTime;
    private boolean isRead;

    public static NotificationResponseDto of(Notification notification) {
        return NotificationResponseDto.builder()
                .id(notification.getId())
                .username(notification.getReservation().getUser().getUsername())
                .hospitalName(notification.getReservation().getHospital().getName())
                .date(notification.getReservation().getReservationSlot().getDate())
                .startTime(notification.getReservation().getReservationSlot().getStartTime())
                .isRead(notification.isRead())
                .build();
    }
}
