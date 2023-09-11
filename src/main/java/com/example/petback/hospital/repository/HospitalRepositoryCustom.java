package com.example.petback.hospital.repository;

import com.example.petback.hospital.OperatingDay;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.species.SpeciesEnum;
import com.example.petback.subject.SubjectEnum;

import java.util.List;

public interface HospitalRepositoryCustom {
    List<Hospital> selectAllHospitalsByFilter(SpeciesEnum speciesEnum, SubjectEnum subjectEnum, OperatingDay operatingDay);
}
