package com.example.petback.reservation.service;

import com.example.petback.hospital.entity.Hospital;
import com.example.petback.hospital.service.HospitalService;
import com.example.petback.reservation.ReservationStatusEnum;
import com.example.petback.reservation.dto.ReservationRequestDto;
import com.example.petback.reservation.dto.ReservationResponseDto;
import com.example.petback.reservation.entity.Reservation;
import com.example.petback.reservationslot.entity.ReservationSlot;
import com.example.petback.reservation.repository.ReservationRepository;
import com.example.petback.reservationslot.repository.ReservationSlotRepository;
import com.example.petback.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService{
    private final ReservationRepository reservationRepository;
    private final HospitalService hospitalService;
    private final ReservationSlotRepository reservationSlotRepository;

    @Override
    public ReservationResponseDto createReservation(User user, ReservationRequestDto requestDto) {
        Hospital hospital = hospitalService.findHospital(requestDto.getHospitalId());
        Optional<ReservationSlot> optionalSlot = reservationSlotRepository
                .findByHospitalAndDateAndStartTime(hospital, requestDto.getDate(), requestDto.getStartTime());
        if (optionalSlot.isEmpty()) {
            throw new IllegalArgumentException("The slot does not exist.");
        }
        ReservationSlot slot = optionalSlot.get();
        if (slot.isReserved()) throw new IllegalArgumentException("이미 예약된 시간입니다.");
        slot.setReserved(true);

        Reservation reservation = Reservation.builder()
                .reservationNum(UUID.randomUUID().toString())
                .hospital(hospital)
                .user(user)
                .reservationSlot(slot)
                .reservationStatus(ReservationStatusEnum.예약완료)
                .build();
        reservationRepository.save(reservation);
        return ReservationResponseDto.of(reservation);
    }

    @Override
    public void deleteReservation(User user, String reservationNum) {
        Reservation reservation = reservationRepository.findById(reservationNum)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약이 존재하지 않습니다."));
        // 예약을 한 유저이거나 해당 병원유저가 아니면
        if (!(user.equals(reservation.getUser()) || user.equals(reservation.getHospital().getUser()))) throw new IllegalArgumentException("해당 예약에 대한 권한이 없습니다.");
        if (reservation.getReservationStatus().equals(ReservationStatusEnum.예약취소)) throw new IllegalArgumentException("이미 취소된 예약입니다.");
        reservation.cancle();
    }

    @Override
    public List<ReservationResponseDto> selectAllReservations(User user) {
        return reservationRepository.findAllByUser(user).stream().map(ReservationResponseDto::of).toList();
    }

    @Override
    public ReservationResponseDto selectReservation(User user, String reservationNum) {
        Reservation reservation = reservationRepository.findById(reservationNum)
                .orElseThrow(()->new IllegalArgumentException("해당 예약이 존재하지 않습니다."));
        if (!(user.equals(reservation.getUser()) || user.equals(reservation.getHospital().getUser()))) throw new IllegalArgumentException("해당 예약에 대한 권한이 없습니다.");
        return ReservationResponseDto.of(reservation);
    }


}
