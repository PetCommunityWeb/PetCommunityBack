package com.example.petback.hospitalspecies.repository;

import com.example.petback.hospitalspecies.entity.HospitalSpecies;
import com.example.petback.species.entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospitalSpeciesRepository extends JpaRepository<HospitalSpecies, Long> {
    Optional<HospitalSpecies> findByHospital_IdAndSpecies(Long id, Species species);

    List<HospitalSpecies> findAllByHospital_Id(Long id);
}
