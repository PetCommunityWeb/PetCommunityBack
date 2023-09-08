package com.example.petback.hospitalspecies.entity;

import com.example.petback.hospital.entity.Hospital;
import com.example.petback.species.entity.Species;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "hospital_species")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@Where(clause = "is_deleted = false")
//@SQLDelete(sql = "UPDATE hospital_species SET is_deleted = true WHERE id = ?")
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
