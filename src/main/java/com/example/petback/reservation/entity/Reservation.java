package com.example.petback.reservation.entity;

import com.example.petback.hospital.entity.Hospital;
import com.example.petback.reservation.ReservationStatusEnum;
import com.example.petback.reservationslot.entity.ReservationSlot;
import com.example.petback.review.entity.Review;
import com.example.petback.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "reservations")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "is_deleted = false")
public class Reservation {
    @Id
    private String reservationNum;

    @Enumerated(value = EnumType.STRING)
    private ReservationStatusEnum reservationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Hospital hospital;
    @ManyToOne(fetch = FetchType.LAZY)
    private ReservationSlot reservationSlot;
    @OneToOne(mappedBy = "reservation")
    private Review review;

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = Boolean.TRUE;
    }
    public void cancle() {
        this.reservationStatus = ReservationStatusEnum.예약취소;
        this.reservationSlot.setReserved(false);
    }
}
