package com.example.petback.reservation.service;

import com.example.petback.reservation.dto.ReservationListResponseDto;
import com.example.petback.reservation.dto.ReservationRequestDto;
import com.example.petback.reservation.dto.ReservationResponseDto;
import com.example.petback.user.entity.User;

public interface ReservationService {

    ReservationResponseDto createReservation(User user, ReservationRequestDto requestDto);

    void deleteReservation(User user, String reservationNum);

    ReservationListResponseDto selectAllReservations(User user);

    ReservationResponseDto selectReservation(User user, String reservationNum);
}
