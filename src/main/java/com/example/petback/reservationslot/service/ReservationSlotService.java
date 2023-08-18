package com.example.petback.reservationslot.service;

import com.example.petback.reservationslot.dto.ReservationSlotRequestDto;
import com.example.petback.reservationslot.dto.SelectReservationSlotRequestDto;
import com.example.petback.reservationslot.dto.SelectReservationSlotResponseDto;
import com.example.petback.user.entity.User;

import java.util.List;

public interface ReservationSlotService {
    // 예약 슬롯 등록
    SelectReservationSlotResponseDto createSlot(User user, ReservationSlotRequestDto requestDto);

    // 예약 슬롯 삭제
    void deleteSlot(User user, Long slotId);

    // 예약 슬롯 조회
    List<SelectReservationSlotResponseDto> selectSlots(SelectReservationSlotRequestDto requestDto);
}
