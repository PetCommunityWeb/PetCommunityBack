package com.example.petback.hospital.dto;

import com.example.petback.common.domain.Address;
import com.example.petback.hospital.entity.Hospital;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HospitalResponseDto {
    private String name;
    private String introduction;
    private String imageUrl;
    private double latitude;
    private double longitude;
    private Address address;
    private String phoneNumber;

    public static HospitalResponseDto of(Hospital hospital){
        return HospitalResponseDto.builder()
                .name(hospital.getName())
                .introduction(hospital.getIntroduction())
                .imageUrl(hospital.getImageUrl())
                .latitude(hospital.getLatitude())
                .longitude(hospital.getLongitude())
                .address(hospital.getAddress())
                .phoneNumber(hospital.getPhoneNumber())
                .build();
    }
}
