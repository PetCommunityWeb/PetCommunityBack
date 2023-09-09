package com.example.petback.species.entity;

import com.example.petback.hospitalspecies.entity.HospitalSpecies;
import com.example.petback.species.SpeciesEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "species")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private SpeciesEnum name;

    @OneToMany(mappedBy = "species")
    private Set<HospitalSpecies> hospitalSpecies;
}
