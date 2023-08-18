package com.example.petback.reservation.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReservationRequestDto {
    private Long hospitalId;
    private LocalDate date;
    private LocalTime startTime;
}
