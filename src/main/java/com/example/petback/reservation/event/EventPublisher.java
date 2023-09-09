package com.example.petback.reservation.event;

import com.example.petback.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Slf4j(topic = "비동기 처리를 위한 Custom EventPublisher")
@Component
@EnableAsync
@RequiredArgsConstructor
public class EventPublisher {
    public final ApplicationEventPublisher eventPublisher;

    @Async
    public void publishEvent(Reservation reservation) {
        log.info("이벤트 생성");
        ReservationEvent reservationEvent = new ReservationEvent(this, reservation);
        eventPublisher.publishEvent(reservationEvent);
    }
}
