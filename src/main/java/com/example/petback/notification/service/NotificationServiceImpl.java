package com.example.petback.notification.service;

import com.example.petback.notification.dto.NotificationResponseDto;
import com.example.petback.notification.entity.Notification;
import com.example.petback.notification.repository.NotificationRepository;
import com.example.petback.reservation.entity.Reservation;
import com.example.petback.reservation.repository.ReservationRepository;
import com.example.petback.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDto> selectNotifications(User user) {
        return notificationRepository.findByReservation_User(user).stream().map(NotificationResponseDto::of).toList();
    }

    @Override
    @Transactional
    public void deleteNotification(Long notificationId, User user) {
        Notification notification = findNotification(notificationId);
        notificationRepository.delete(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public Notification findNotification(Long id) {
        return notificationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("알림이 존재하지 않습니다.")
        );
    }

    @Scheduled(fixedRate = 60000)
    public void checkReservation() {
        log.info("스케쥴러 실행");
        List<Reservation> reservations = reservationRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeMinutesLater = now.plusMinutes(5);
        for (Reservation reservation : reservations) {
            LocalTime reservationTime = reservation.getReservationSlot().getStartTime();
            Duration difference = Duration.between(reservationTime, threeMinutesLater);
            if (difference.getSeconds() < 60) {
                log.info("5분전");
                sendNotification(reservation);
            }
        }
    }

    private void sendNotification(Reservation reservation) {
        Notification notification = Notification.builder()
                .reservation(reservation)
                .build();
        notificationRepository.save(notification);
    }
}
