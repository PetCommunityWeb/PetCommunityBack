package com.example.petback.reservation.dto;

import com.example.petback.reservation.ReservationStatusEnum;
import com.example.petback.reservation.entity.Reservation;
import com.example.petback.review.dto.ReviewResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDto {
    private String reservationNum;
    private String username;
    private String hospitalName;
    private ReservationStatusEnum status;
    private String date;
    private String startTime;
    private ReviewResponseDto review;

    public static ReservationResponseDto of(Reservation reservation){
        return ReservationResponseDto.builder()
                .review(ReviewResponseDto.of(reservation.getReview()))
                .reservationNum(reservation.getReservationNum())
                .username(reservation.getUser().getUsername())
                .hospitalName(reservation.getHospital().getName())
                .date(reservation.getReservationSlot().getDate().toString())
                .startTime(reservation.getReservationSlot().getStartTime().toString())
//                .date(toEpochMillis(reservation.getReservationSlot().getDate()))
//                .startTime(toEpochSeconds(reservation.getReservationSlot().getStartTime()))
                .status(reservation.getReservationStatus())
                .build();
    }

    private static long toEpochMillis(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    private static long toEpochSeconds(LocalTime localTime) {
        return localTime.toSecondOfDay();
    }
}
