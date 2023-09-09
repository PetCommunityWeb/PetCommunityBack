package com.example.petback.hospital.repository;

import com.example.petback.hospital.dto.HospitalResponseDto;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.species.SpeciesEnum;
import com.example.petback.subject.SubjectEnum;
import com.example.petback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, Long>, HospitalRepositoryCustom{
    Optional<Hospital> findByName(String name);
    List<Hospital> findAllByUser(User user);
    List<Hospital> findAllByHospitalSpecies_Species_NameAndHospitalSubjects_Subject_Name(SpeciesEnum speciesEnum, SubjectEnum subjectEnum);
    List<Hospital> findAllByHospitalSpecies_Species_Name(SpeciesEnum speciesEnum);
    List<Hospital> findAllByHospitalSubjects_Subject_Name(SubjectEnum subjectEnum);
}
