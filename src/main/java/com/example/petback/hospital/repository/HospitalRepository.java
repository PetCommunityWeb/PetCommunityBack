package com.example.petback.hospital.repository;

import com.example.petback.hospital.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByName(String 테스트병원1);
}
