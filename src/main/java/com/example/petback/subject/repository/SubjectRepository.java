package com.example.petback.subject.repository;

import com.example.petback.subject.SubjectEnum;
import com.example.petback.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByName(SubjectEnum subjectEnum);
}
