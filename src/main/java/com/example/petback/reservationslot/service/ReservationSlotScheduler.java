package com.example.petback.reservationslot.service;

import com.example.petback.hospital.OperatingDay;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.hospital.repository.HospitalRepository;
import com.example.petback.reservationslot.entity.ReservationSlot;
import com.example.petback.reservationslot.repository.ReservationSlotRepository;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationSlotScheduler {

    private final HospitalRepository hospitalRepository; // 병원 정보를 가져오기 위한 레포지토리

    private final ReservationSlotRepository reservationSlotRepository; // 예약 슬롯 관련 서비스


//     매일 오전 4시에 실행될 메서드
    @Transactional
    @Scheduled(cron = "0 0 4 * * ?")
    @SchedulerLock(name = "addReservationSlotsLock", lockAtMostFor = "10m", lockAtLeastFor = "5m")
    public void addReservationSlots() {
        LocalDate twoMonthsLater = LocalDate.now().plusMonths(2);
        OperatingDay dayOfWeek = OperatingDay.valueOf(twoMonthsLater.getDayOfWeek().name());
        List<Hospital> allHospitals = hospitalRepository.findAll(); // 모든 병원 정보를 가져옵니다.
        for (Hospital hospital : allHospitals) {
            hospital.getOperatingDays().forEach(System.out::println);
            if (hospital.getOperatingDays().contains(dayOfWeek)) {
                // 예약 슬롯을 추가하는 로직
                LocalTime time = LocalTime.of(9, 0); // 시작 시간은 오전 9시
                for (int i = 0; i < 9; i++) { // 9시부터 5시까지
                    ReservationSlot slot = ReservationSlot.builder()
                            .date(twoMonthsLater)
                            .startTime(time)
                            .isReserved(false)
                            .hospital(hospital)
                            .build();
                    reservationSlotRepository.save(slot);
                    time = time.plusHours(1);
                }
            }
        }
    }
}
