package com.example.petback.reservation.repository;

import com.example.petback.reservation.dto.ReservationResponseDto;
import com.example.petback.reservation.entity.Reservation;
import com.example.petback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findByReservationSlot_StartTimeBetween(LocalTime startTimeStart, LocalTime startTimeEnd);
    List<Reservation> findByReservationSlot_StartTimeBefore(LocalTime startTime);
    List<Reservation> findByUser(User user);
    List<Reservation> findAllByUser(User user);
}
