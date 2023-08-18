package com.example.petback.hospital.service;

import com.example.petback.hospital.dto.HospitalRequestDto;
import com.example.petback.hospital.dto.HospitalResponseDto;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.hospital.repository.HospitalRepository;
import com.example.petback.user.entity.User;
import com.example.petback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HospitalServiceImpl implements HospitalService{
    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;

    @Override
    public HospitalResponseDto createHospital(User user, HospitalRequestDto requestDto) {
        user = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("유저 정보가 존재하지 않습니다."));
        Hospital hospital = requestDto.toEntity();
        hospital.setUser(user);
        hospitalRepository.save(hospital);
        return HospitalResponseDto.of(hospital);
    }

    @Transactional(readOnly = true)
    public List<HospitalResponseDto> selectAllHospitals() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        return hospitals.stream().map(HospitalResponseDto::of).toList();
    }

    @Transactional(readOnly = true)
    public HospitalResponseDto selectHospital(Long id) {
        Hospital hospital = findHospital(id);
        return HospitalResponseDto.of(hospital);
    }

    public HospitalResponseDto updateHospital(User user, Long id, HospitalRequestDto requestDto) {
        Hospital hospital = findHospital(id);
        if (!user.equals(hospital.getUser())) throw new IllegalArgumentException("병원 수정 권한이 없습니다.");
        hospital.updateName(requestDto.getName())
                .updateIntroduction(requestDto.getIntroduction())
                .updateImageUrl(requestDto.getImageUrl())
                .updateLatitude(requestDto.getLatitude())
                .updateLongitude(requestDto.getLongitude())
                .updateAddress(requestDto.getAddress())
                .updatePhoneNumber(requestDto.getPhoneNumber());
        return HospitalResponseDto.of(hospital);
    }

    public void deleteHospital(User user, Long id) {
        Hospital hospital = findHospital(id);
        if (!user.equals(hospital.getUser())) throw new IllegalArgumentException("병원 수정 권한이 없습니다.");
        hospitalRepository.delete(hospital);
    }
    private Hospital findHospital(Long id){
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 병원입니다."));
    }
}
