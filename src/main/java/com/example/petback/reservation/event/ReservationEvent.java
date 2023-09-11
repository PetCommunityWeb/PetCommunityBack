package com.example.petback.reservation.event;

import com.example.petback.reservation.entity.Reservation;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ReservationEvent extends ApplicationEvent {
    private final Reservation reservation;

    public ReservationEvent(Object source, Reservation reservation) {
        super(source);
        this.reservation = reservation;
    }
}
