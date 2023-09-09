package com.example.petback.hospitalspecies.entity;

import com.example.petback.hospital.entity.Hospital;
import com.example.petback.species.entity.Species;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "hospital_species", indexes = @Index(name = "index_species", columnList = "species_id"))
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class HospitalSpecies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    private Species species;

//    @Builder.Default
//    private boolean isDeleted = Boolean.FALSE;
}
