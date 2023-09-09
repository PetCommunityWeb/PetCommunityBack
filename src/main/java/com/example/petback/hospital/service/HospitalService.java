package com.example.petback.hospital.service;

import com.example.petback.hospital.OperatingDay;
import com.example.petback.hospital.dto.HospitalListResponseDto;
import com.example.petback.hospital.dto.HospitalRequestDto;
import com.example.petback.hospital.dto.HospitalResponseDto;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.species.SpeciesEnum;
import com.example.petback.subject.SubjectEnum;
import com.example.petback.user.entity.User;

public interface HospitalService {
    HospitalResponseDto createHospital(User user, HospitalRequestDto requestDto);
    @Deprecated
    HospitalListResponseDto selectAllHospitals();
    HospitalListResponseDto selectAllHospitalsByFilter(SpeciesEnum speciesEnum, SubjectEnum subjectEnum, OperatingDay operatingDay);
    HospitalResponseDto selectHospital(Long id);
    HospitalResponseDto updateHospital(User user, Long id, HospitalRequestDto requestDto);
    Hospital findHospital(Long id);
    void deleteHospital(User user, Long id);
    HospitalListResponseDto selectMyHospitals(User user);
}
