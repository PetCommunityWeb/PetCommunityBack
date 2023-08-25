package com.example.petback.reservation.dto;

import com.example.petback.reservation.ReservationStatusEnum;
import com.example.petback.reservation.entity.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class ReservationResponseDto {
    private String reservationNum;
    private String username;
    private String hospitalName;
    private ReservationStatusEnum status;
    private LocalDate date;
    private LocalTime startTime;

    public static ReservationResponseDto of(Reservation reservation){
        return ReservationResponseDto.builder()
                .reservationNum(reservation.getReservationNum())
                .username(reservation.getUser().getUsername())
                .hospitalName(reservation.getHospital().getName())
                .date(reservation.getReservationSlot().getDate())
                .startTime(reservation.getReservationSlot().getStartTime())
                .status(reservation.getReservationStatus())
                .build();
    }
}
