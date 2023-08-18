package com.example.petback.reservationslot.controller;

import com.example.petback.common.security.UserDetailsImpl;
import com.example.petback.reservationslot.dto.ReservationSlotRequestDto;
import com.example.petback.reservationslot.dto.SelectReservationSlotRequestDto;
import com.example.petback.reservationslot.dto.SelectReservationSlotResponseDto;
import com.example.petback.reservationslot.service.ReservationSlotService;
import com.example.petback.reservationslot.service.ReservationSlotServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation-slot")
public class ReservationSlotController {

    private final ReservationSlotService reservationSlotService;

    @PostMapping
    public ResponseEntity createSlot(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ReservationSlotRequestDto requestDto){
        SelectReservationSlotResponseDto responseDto = reservationSlotService.createSlot(userDetails.getUser(), requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{slotId}")
    public ResponseEntity deleteSlot(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long slotId){
        reservationSlotService.deleteSlot(userDetails.getUser(), slotId);
        return ResponseEntity.ok().body("슬롯 삭제가 완료되었습니다.");
    }

    @GetMapping
    public ResponseEntity selectSlots(@RequestBody SelectReservationSlotRequestDto requestDto){
        List<SelectReservationSlotResponseDto> responseDto = reservationSlotService.selectSlots(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }
}
