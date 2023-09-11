package com.example.petback.hospital.repository;

import com.example.petback.hospital.dto.HospitalResponseDto;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByName(String 테스트병원1);

    List<Hospital> findAllByUser(User user);

    @Query(value = "SELECT * FROM hospitals WHERE user_id = :userId", nativeQuery = true)
    List<Hospital> findSoftDeletedHospitalsByUserId(@Param("userId") Long userId);
}
