package com.example.petback.notification.controller;

import com.example.petback.common.security.UserDetailsImpl;
import com.example.petback.notification.dto.NotificationResponseDto;
import com.example.petback.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    
    // 유저 알람 전체 조회
    @GetMapping
    public ResponseEntity selectNotifications(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<NotificationResponseDto> responseDtos = notificationService
                .selectNotifications(userDetails.getUser()).getNotificationResponseDtos();
        return ResponseEntity.ok().body(responseDtos);
    }

    // 알람 삭제
    @DeleteMapping("/{notificationId}")
    public ResponseEntity deleteNotification(@PathVariable Long notificationId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        notificationService.deleteNotification(notificationId, userDetails.getUser());
        return ResponseEntity.ok().body("알림 삭제가 완료되었습니다.");
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity readNotification(@PathVariable Long notificationId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        notificationService.readNotification(notificationId, userDetails.getUser());
        return ResponseEntity.ok().body("알림을 읽었습니다.");
    }
}
