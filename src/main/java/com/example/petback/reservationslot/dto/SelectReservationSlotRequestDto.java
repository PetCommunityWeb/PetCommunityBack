package com.example.petback.reservationslot.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SelectReservationSlotRequestDto {
    private Long hospitalId;
    private LocalDate date;
}
