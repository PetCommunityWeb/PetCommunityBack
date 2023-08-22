package com.example.petback.species.repository;

import com.example.petback.species.SpeciesEnum;
import com.example.petback.species.entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpeciesRepository extends JpaRepository<Species, Long> {
    Optional<Species> findByName(SpeciesEnum speciesEnum);
}
