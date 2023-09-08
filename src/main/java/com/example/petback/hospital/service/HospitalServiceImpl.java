package com.example.petback.hospital.service;

import com.example.petback.hospital.OperatingDay;
import com.example.petback.hospital.dto.HospitalListResponseDto;
import com.example.petback.hospital.dto.HospitalRequestDto;
import com.example.petback.hospital.dto.HospitalResponseDto;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.hospital.repository.HospitalRepository;
import com.example.petback.hospitalspecies.entity.HospitalSpecies;
import com.example.petback.hospitalsubject.entity.HospitalSubject;
import com.example.petback.reservationslot.entity.ReservationSlot;
import com.example.petback.species.SpeciesEnum;
import com.example.petback.species.entity.Species;
import com.example.petback.species.repository.SpeciesRepository;
import com.example.petback.subject.SubjectEnum;
import com.example.petback.subject.entity.Subject;
import com.example.petback.subject.repository.SubjectRepository;
import com.example.petback.user.entity.User;
import com.example.petback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class HospitalServiceImpl implements HospitalService{
    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;
    private final SpeciesRepository speciesRepository;
    private final SubjectRepository subjectRepository;
    @Override
    @Caching(evict = {
            @CacheEvict(value = "allHospitals"),
            @CacheEvict(value = "myHospitals", key = "#user.id"),
    })
    public HospitalResponseDto createHospital(User user, HospitalRequestDto requestDto) {
        user = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("유저 정보가 존재하지 않습니다."));
        Hospital hospital = requestDto.toEntity();
        hospital.setUser(user);
        List<SpeciesEnum> speciesEnums = requestDto.getSpeciesEnums();
        List<SubjectEnum> subjectEnums = requestDto.getSubjectEnums();
        addSpecies(hospital, speciesEnums);
        addSubjects(hospital, subjectEnums);


        List<ReservationSlot> reservationSlots = createDefaultReservationSlots(hospital);
        hospital.setReservationSlots(reservationSlots);

        hospitalRepository.save(hospital);
        return HospitalResponseDto.of(hospital);
    }

    private List<ReservationSlot> createDefaultReservationSlots(Hospital hospital) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusMonths(2); // 2달간의 예약슬롯 추가
        List<ReservationSlot> slots = new ArrayList<>();
        Set<OperatingDay> operatingDays = hospital.getOperatingDays();

        while (!today.isAfter(endDate)) {
            OperatingDay dayOfWeek = OperatingDay.valueOf(today.getDayOfWeek().name());
            if (operatingDays.contains(dayOfWeek)) {  // 선택된 요일에만 슬롯 생성
                LocalTime time = LocalTime.of(9, 0); // 시작 시간은 오전 9시
                for (int i = 0; i < 9; i++) { // 9시부터 5시까지
                    ReservationSlot slot = ReservationSlot.builder()
                            .date(today)
                            .startTime(time)
                            .isReserved(false)
                            .hospital(hospital)
                            .build();
                    slots.add(slot);
                    time = time.plusHours(1);
                }
            }
            today = today.plusDays(1);
        }
        return slots;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "myHospitals", key = "#user.id")
    public HospitalListResponseDto selectMyHospitals(User user) {
        List<Hospital> hospitals = hospitalRepository.findAllByUser(user);
        return HospitalListResponseDto.builder()
                .hospitalResponseDtos(hospitals.stream().map(HospitalResponseDto::of).toList())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "allHospitals")
    public HospitalListResponseDto selectAllHospitals() {
        List<Hospital> hospitals = hospitalRepository.findAll();

        return HospitalListResponseDto.builder()
                .hospitalResponseDtos(hospitals.stream().map(HospitalResponseDto::of).toList())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "hospital")
    public HospitalResponseDto selectHospital(Long id) {
        Hospital hospital = findHospital(id);
        return HospitalResponseDto.of(hospital);
    }


    @Override
    @Caching(evict = {
            @CacheEvict(value = "allHospitals"),
            @CacheEvict(value = "myHospitals", key = "#user.id"),
            @CacheEvict(value = "hospital", key = "#id")
    })
    public HospitalResponseDto updateHospital(User user, Long id, HospitalRequestDto requestDto) {
        Hospital hospital = findHospital(id);
        if (!user.equals(hospital.getUser())) throw new IllegalArgumentException("병원 수정 권한이 없습니다.");
        // 기존 연관관계 삭제
        hospital.resetHospitalSpecies();
        // requestDto로 받은 값으로 변경
        List<SpeciesEnum> speciesEnums = requestDto.getSpeciesEnums();
        List<SubjectEnum> subjectEnums = requestDto.getSubjectEnums();
        addSpecies(hospital, speciesEnums);
        addSubjects(hospital, subjectEnums);
        hospital.updateName(requestDto.getName())
                .updateIntroduction(requestDto.getIntroduction())
                .updateImageUrl(requestDto.getImageUrl())
                .updateLatitude(requestDto.getLatitude())
                .updateLongitude(requestDto.getLongitude())
                .updateAddress(requestDto.getAddress())
                .updatePhoneNumber(requestDto.getPhoneNumber())
                .updateOperatingDays(requestDto.getOperatingDays());
        return HospitalResponseDto.of(hospital);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "allHospitals"),
            @CacheEvict(value = "myHospitals", key = "#user.id"),
            @CacheEvict(value = "hospital", key = "#id")
    })
    public void deleteHospital(User user, Long id) {
        Hospital hospital = findHospital(id);
        if (!user.equals(hospital.getUser())) throw new IllegalArgumentException("병원 수정 권한이 없습니다.");
        hospitalRepository.delete(hospital);
    }

    public Hospital findHospital(Long id){
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 병원입니다."));
    }
    private void addSpecies(Hospital hospital, List<SpeciesEnum> speciesEnums){
        for (SpeciesEnum speciesEnum : speciesEnums) {
            Species species = speciesRepository.findByName(speciesEnum)
                    .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 종입니다."));
            HospitalSpecies hospitalSpecies = HospitalSpecies.builder()
                    .hospital(hospital)
                    .species(species)
                    .build();
            hospital.addHospitalSpecies(hospitalSpecies);
        }
    }

    private void addSubjects(Hospital hospital, List<SubjectEnum> subjectEnums) {
        for (SubjectEnum subjectEnum : subjectEnums) {
            Subject subject = subjectRepository.findByName(subjectEnum)
                    .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 종입니다."));
            HospitalSubject hospitalSubject = HospitalSubject.builder()
                    .hospital(hospital)
                    .subject(subject)
                    .build();
            hospital.addHospitalSubject(hospitalSubject);
        }
    }
}
