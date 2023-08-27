package com.example.petback.hospital.dto;

import com.example.petback.common.domain.Address;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.species.SpeciesEnum;
import com.example.petback.subject.SubjectEnum;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter
public class HospitalResponseDto {
    private Long id;
    private String name;
    private String introduction;
    private String imageUrl;
    private double latitude;
    private double longitude;
    private String address;
    private String phoneNumber;
    private String ownerEmail;
    private List<SpeciesEnum> speciesEnums;
    private List<SubjectEnum> subjectEnums;

    public static HospitalResponseDto of(Hospital hospital){
        return HospitalResponseDto.builder()
                .id(hospital.getId())
                .name(hospital.getName())
                .introduction(hospital.getIntroduction())
                .imageUrl(hospital.getImageUrl())
                .latitude(hospital.getLatitude())
                .longitude(hospital.getLongitude())
                .address(hospital.getAddress())
                .phoneNumber(hospital.getPhoneNumber())
                .ownerEmail(hospital.getUser().getEmail())
                .speciesEnums(
                        hospital.getHospitalSpecies().stream()
                                .map(hospitalSpecies -> hospitalSpecies.getSpecies().getName())
                                .toList()
                )
                .subjectEnums(
                        hospital.getHospitalSubjects().stream()
                                .map(hospitalSpecies -> hospitalSpecies.getSubject().getName())
                                .toList()
                )
                .build();
    }
}
