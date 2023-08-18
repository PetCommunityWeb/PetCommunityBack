package com.example.petback.reservation.controller;

import com.example.petback.common.security.UserDetailsImpl;
import com.example.petback.reservation.dto.ReservationRequestDto;
import com.example.petback.reservation.dto.ReservationResponseDto;
import com.example.petback.reservation.service.ReservationService;
import com.example.petback.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    @PostMapping
    public ResponseEntity createReservation(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ReservationRequestDto requestDto){
        ReservationResponseDto responseDto = reservationService.createReservation(userDetails.getUser(), requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{reservationNum}")
    public ResponseEntity deleteReservation(@AuthenticationPrincipal UserDetailsImpl userDetail, @PathVariable String reservationNum){
        reservationService.deleteReservation(userDetail.getUser(), reservationNum);
        return ResponseEntity.ok().body("예약 취소가 완료되었습니다.");
    }

    @GetMapping
    public ResponseEntity selectAllReservations(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<ReservationResponseDto> responseDtos = reservationService.selectAllReservations(userDetails.getUser());
        return ResponseEntity.ok().body(responseDtos);
    }

    @GetMapping("/{reservationNum}")
    public ResponseEntity selectReservation(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable String reservationNum){
        ReservationResponseDto responseDto = reservationService.selectReservation(userDetails.getUser(), reservationNum);
        return ResponseEntity.ok().body(responseDto);
    }
}
