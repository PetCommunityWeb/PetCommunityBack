package com.example.petback.reservationslot.entity;

import com.example.petback.hospital.entity.Hospital;
import com.example.petback.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservation_slots")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime startTime;
    private boolean isReserved;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hospital hospital;

    @Builder.Default
    @OneToMany(mappedBy = "reservationSlot")
    private List<Reservation> reservations = new ArrayList<>();
}
