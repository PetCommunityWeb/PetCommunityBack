package com.example.petback.notification.dto;

import com.example.petback.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDto {
    private Long id;
    private String username;
    private String hospitalName;
    private String date;
    private String startTime;
    private boolean read;

    public static NotificationResponseDto of(Notification notification) {
        return NotificationResponseDto.builder()
                .id(notification.getId())
                .username(notification.getReservation().getUser().getUsername())
                .hospitalName(notification.getReservation().getHospital().getName())
                .date(notification.getReservation().getReservationSlot().getDate().toString())
                .startTime(notification.getReservation().getReservationSlot().getStartTime().toString())
                .read(notification.isRead())
                .build();
    }
}
