package com.example.petback.hospitalsubject.repository;

import com.example.petback.hospitalsubject.entity.HospitalSubject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalSubjectRepository extends JpaRepository<HospitalSubject, Long> {
}
