package com.example.petback.reservation.repository;

import com.example.petback.reservation.dto.ReservationResponseDto;
import com.example.petback.reservation.entity.Reservation;
import com.example.petback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findByReservationSlot_StartTimeBetween(LocalTime startTimeStart, LocalTime startTimeEnd);
    List<Reservation> findAllByUserOrderByReservationSlotDateDescReservationSlotStartTimeDesc(User user);

    @Query(value = "SELECT * FROM reservations WHERE user_id = :userId", nativeQuery = true)
    List<Reservation> findSoftDeletedReservationsByUserId(Long id);
}
