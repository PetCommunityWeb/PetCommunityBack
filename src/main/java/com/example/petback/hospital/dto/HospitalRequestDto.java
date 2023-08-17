package com.example.petback.hospital.dto;

import com.example.petback.common.domain.Address;
import com.example.petback.hospital.entity.Hospital;
import lombok.Getter;
import lombok.ToString;

@Getter
public class HospitalRequestDto {
    private String name;
    private String introduction;
    private String imageUrl;
    private double latitude;
    private double longitude;
    private Address address;
    private String phoneNumber;

    public Hospital toEntity(){
        return Hospital.builder()
                .name(name)
                .introduction(introduction)
                .imageUrl(imageUrl)
                .latitude(latitude)
                .longitude(longitude)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
    }
}
