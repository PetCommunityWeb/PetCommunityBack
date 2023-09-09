package com.example.petback.hospital.dto;

import com.example.petback.hospital.OperatingDay;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.reservation.entity.Reservation;
import com.example.petback.review.dto.ReviewResponseDto;
import com.example.petback.species.SpeciesEnum;
import com.example.petback.subject.SubjectEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private List<ReviewResponseDto> reviews;
    private List<OperatingDay> operatingDays; // 병원의 운영 요일

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
                .reviews(
                        hospital.getReservations().stream()
                                .map(Reservation::getReview)
                                .filter(Objects::nonNull) // null이 아닌 리뷰만 선택
                                .map(ReviewResponseDto::of)
                                .toList()
                )
                .operatingDays(
                        hospital.getOperatingDays().stream()
                                .sorted()
                                .toList()
                )
                .build();
    }
}
