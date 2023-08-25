package com.example.petback.reservationslot.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReservationSlotRequestDto {
    private Long hospitalId;
    private LocalDate date;
    private LocalTime startTime;
}
