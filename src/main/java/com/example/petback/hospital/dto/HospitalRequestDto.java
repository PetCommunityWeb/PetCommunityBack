package com.example.petback.hospital.dto;

import com.example.petback.hospital.OperatingDay;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.species.SpeciesEnum;
import com.example.petback.subject.SubjectEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HospitalRequestDto {
    private String name;
    private String introduction;
    private String imageUrl;
    private double latitude;
    private double longitude;
    private String address;
    private String phoneNumber;
    private List<SpeciesEnum> speciesEnums;
    private List<SubjectEnum> subjectEnums;
    private Set<OperatingDay> operatingDays;

    public Hospital toEntity(){
        return Hospital.builder()
                .name(name)
                .introduction(introduction)
                .imageUrl(imageUrl)
                .latitude(latitude)
                .longitude(longitude)
                .address(address)
                .phoneNumber(phoneNumber)
                .operatingDays(operatingDays)  // 추가된 부분
                .build();
    }
}
