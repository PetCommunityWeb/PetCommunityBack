package com.example.petback.reservation.event;

import com.example.petback.notification.entity.Notification;
import com.example.petback.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j(topic = "Event Listener")
@Component
@RequiredArgsConstructor
public class ReservationEventListener implements ApplicationListener<ReservationEvent> {
    private final NotificationRepository notificationRepository;

    @Override
    @TransactionalEventListener
    public void onApplicationEvent(ReservationEvent event) {
        Notification notification = Notification.builder()
                .reservation(event.getReservation())
                .build();
        notificationRepository.save(notification);
    }
}
