package com.example.petback.reservation.entity;

import com.example.petback.hospital.entity.Hospital;
import com.example.petback.reservation.ReservationStatusEnum;
import com.example.petback.reservationslot.entity.ReservationSlot;
import com.example.petback.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.ToOne;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Reservation {
    @Id
    private String reservationNum;

    @Enumerated(value = EnumType.STRING)
    private ReservationStatusEnum reservationStatus;

    @ManyToOne
    private User user;
    @ManyToOne
    private Hospital hospital;
    @ManyToOne(fetch = FetchType.LAZY)
    private ReservationSlot reservationSlot;

    public void cancle() {
        this.reservationStatus = ReservationStatusEnum.예약취소;
        this.reservationSlot.setReserved(false);
    }
}
