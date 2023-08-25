package com.example.petback.reservationslot.repository;

import com.example.petback.hospital.entity.Hospital;
import com.example.petback.reservation.entity.Reservation;
import com.example.petback.reservationslot.entity.ReservationSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservationSlotRepository extends JpaRepository<ReservationSlot, Long> {
    Optional<ReservationSlot> findByDateAndStartTime(LocalDate date, LocalTime startTime);
//    @Query("SELECT rs FROM ReservationSlot rs WHERE rs.hospital = :hospital " +
//            "AND rs.reservation IS NULL " +
//            "AND rs.startDateTime >= :startTime " +
//            "AND rs.endDateTime <= :endTime " +
//            "ORDER BY rs.startDateTime")
//    List<ReservationSlot> findAvailableSlots(Hospital hospital, LocalDateTime startTime, LocalDateTime endTime);

    Optional<ReservationSlot> findByHospitalAndDateAndStartTime(Hospital hospital, LocalDate date, LocalTime startTime);
    List<ReservationSlot> findAllByHospital_IdAndDate(Long hospitalId, LocalDate date);
}
