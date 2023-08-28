package com.example.petback.hospital.service;

import com.example.petback.hospital.dto.HospitalRequestDto;
import com.example.petback.hospital.dto.HospitalResponseDto;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.user.entity.User;

import java.util.List;

public interface HospitalService {
    HospitalResponseDto createHospital(User user, HospitalRequestDto requestDto);
    List<HospitalResponseDto> selectAllHospitals();
    HospitalResponseDto selectHospital(Long id);
    HospitalResponseDto updateHospital(User user, Long id, HospitalRequestDto requestDto);
    Hospital findHospital(Long id);
    void deleteHospital(User user, Long id);

    List<HospitalResponseDto> selectMyHospitals(User user);
}
