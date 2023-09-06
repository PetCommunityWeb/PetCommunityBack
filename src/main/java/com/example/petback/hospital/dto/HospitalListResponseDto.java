package com.example.petback.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitalListResponseDto {
    private List<HospitalResponseDto> hospitalResponseDtos;
}
