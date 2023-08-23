package com.example.petback.reservation.dto;

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
public class ReservationRequestDto {
    private Long hospitalId;
    private LocalDate date;
    private LocalTime startTime;
}
