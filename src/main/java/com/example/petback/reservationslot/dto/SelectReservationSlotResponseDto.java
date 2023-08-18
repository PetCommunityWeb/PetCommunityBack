package com.example.petback.reservationslot.dto;

import com.example.petback.reservationslot.entity.ReservationSlot;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class SelectReservationSlotResponseDto {
    private Long slotId;
    private Long hospitalId;
    private LocalDate date;
    private LocalTime startTime;
    private boolean isReserved;
    public static SelectReservationSlotResponseDto of(ReservationSlot reservationSlot) {
        SelectReservationSlotResponseDto responseDto =
                SelectReservationSlotResponseDto.builder()
                        .slotId(reservationSlot.getId())
                        .hospitalId(reservationSlot.getHospital().getId())
                        .date(reservationSlot.getDate())
                        .startTime(reservationSlot.getStartTime())
                        .isReserved(reservationSlot.isReserved())
                        .build();
        return responseDto;
    }
}
