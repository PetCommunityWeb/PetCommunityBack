package com.example.petback.hospital.repository;

import com.example.petback.hospital.OperatingDay;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.hospital.entity.QHospital;
import com.example.petback.species.SpeciesEnum;
import com.example.petback.subject.SubjectEnum;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HospitalRepositoryImpl implements HospitalRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Hospital> selectAllHospitalsByFilter(SpeciesEnum speciesEnum, SubjectEnum subjectEnum, OperatingDay operatingDay) {
        QHospital hospital = QHospital.hospital;

        BooleanBuilder builder = new BooleanBuilder();

        if (speciesEnum != null) {
            builder.and(hospital.hospitalSpecies.any().species.name.eq(speciesEnum));
        }

        if (subjectEnum != null) {
            builder.and(hospital.hospitalSubjects.any().subject.name.eq(subjectEnum));
        }

        if (operatingDay != null) {
            builder.and(hospital.operatingDays.contains(operatingDay));
        }

        List<Hospital> hospitals = queryFactory
                .selectFrom(hospital)
                .where(builder)
                .fetch();

        return hospitals;
    }
}
