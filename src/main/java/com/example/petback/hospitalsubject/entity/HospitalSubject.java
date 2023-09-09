package com.example.petback.hospitalsubject.entity;

import com.example.petback.hospital.entity.Hospital;
import com.example.petback.subject.entity.Subject;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hospital_subjects", indexes = @Index(name = "index_subject", columnList = "subject_id"))
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HospitalSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;
}
