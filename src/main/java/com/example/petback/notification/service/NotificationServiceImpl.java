package com.example.petback.notification.service;

import com.example.petback.notification.dto.NotificationListResponseDto;
import com.example.petback.notification.dto.NotificationResponseDto;
import com.example.petback.notification.entity.Notification;
import com.example.petback.notification.repository.NotificationRepository;
import com.example.petback.reservation.entity.Reservation;
import com.example.petback.reservation.repository.ReservationRepository;
import com.example.petback.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "myNotification", key = "#user.id")
    public NotificationListResponseDto selectNotifications(User user) {
        return NotificationListResponseDto.builder()
                .notificationResponseDtos(
                        notificationRepository
                                .findByReservation_UserOrderByCreatedAtDesc(user).stream().map(NotificationResponseDto::of).toList())
                .build();
    }

    @Override
    @Transactional
    @CacheEvict(value = "myNotification", key = "#user.id")
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

    @Override
    @Transactional
    @CacheEvict(value = "myNotification", key = "#user.id")
    public void readNotification(Long id, User user) {
        Notification notification = notificationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("알림이 존재하지 않습니다."));
        notification.read();
    }

    @Transactional
    @Scheduled(fixedRate = 60000 * 10)
    @SchedulerLock(name = "checkReservationLock", lockAtMostFor = "10m", lockAtLeastFor = "10m")
    public void checkReservation() {
        log.info("스케쥴러 실행");
        LocalDateTime later = LocalDateTime.now().plusMinutes(6);
        List<Reservation> reservations = reservationRepository.findByReservationSlot_StartTimeBetween(LocalTime.from(LocalDateTime.now()), LocalTime.from(later));
        for (Reservation reservation : reservations) {
            LocalTime reservationTime = reservation.getReservationSlot().getStartTime();
            Duration difference = Duration.between(reservationTime, later);
            if (Math.abs(difference.getSeconds()) < 60) {
                log.info("5분전");
                sendNotification(reservation);
            }
        }
    }

    @CacheEvict(value = "myNotifications", key = "#reservation.user.id")
    public void sendNotification(Reservation reservation) {
        Notification notification = Notification.builder()
                .reservation(reservation)
                .build();
        notificationRepository.save(notification);
    }
}
