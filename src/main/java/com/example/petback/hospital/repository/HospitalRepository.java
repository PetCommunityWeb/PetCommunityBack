package com.example.petback.hospital.repository;

import com.example.petback.hospital.dto.HospitalResponseDto;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByName(String 테스트병원1);

    List<Hospital> findAllByUser(User user);
}
