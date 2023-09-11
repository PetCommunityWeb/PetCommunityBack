package com.example.petback.reservationslot.service;

import com.example.petback.hospital.entity.Hospital;
import com.example.petback.hospital.repository.HospitalRepository;
import com.example.petback.reservationslot.dto.ReservationSlotRequestDto;
import com.example.petback.reservationslot.dto.SelectReservationSlotResponseDto;
import com.example.petback.reservationslot.entity.ReservationSlot;
import com.example.petback.reservationslot.repository.ReservationSlotRepository;
import com.example.petback.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationSlotServiceImpl implements ReservationSlotService{
    private final ReservationSlotRepository reservationSlotRepository;
    private final HospitalRepository hospitalRepository;

    // 예약 슬롯 등록
    @Override
    public SelectReservationSlotResponseDto createSlot(User user, ReservationSlotRequestDto requestDto) {
        Hospital hospital = hospitalRepository.findById(requestDto.getHospitalId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 병원입니다."));
        if (!hospital.getUser().equals(user)) throw new IllegalArgumentException("해당 슬롯 관련 권한이 없습니다.");
        ReservationSlot reservationSlot = ReservationSlot.builder()
                .hospital(hospital)
                .isReserved(false)
                .date(requestDto.getDate())
                .startTime(requestDto.getStartTime())
                .build();
        return SelectReservationSlotResponseDto.of(reservationSlotRepository.save(reservationSlot));
    }

    // 예약 슬롯 삭제
    @Override

    public void deleteSlot(User user, Long slotId) {
        ReservationSlot reservationSlot = reservationSlotRepository.findById(slotId)
                .orElseThrow(() -> new IllegalArgumentException("해당 슬롯이 존재하지 않습니다."));
        if (!user.equals(reservationSlot.getHospital().getUser())) throw new IllegalArgumentException("해당 슬롯 관련 권한이 없습니다.");
        reservationSlotRepository.delete(reservationSlot);
    }

    // 예약 슬롯 조회
    @Override
    public List<SelectReservationSlotResponseDto> selectSlots(Long hospitalId, LocalDate date) {
        return reservationSlotRepository
                .findAllByHospital_IdAndDate(hospitalId, date)
                .stream().map(SelectReservationSlotResponseDto::of).toList();
    }
}
