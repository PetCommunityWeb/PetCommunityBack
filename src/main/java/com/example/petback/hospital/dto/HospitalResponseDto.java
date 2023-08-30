package com.example.petback.hospital.dto;

import com.example.petback.common.domain.Address;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.reservation.dto.ReservationResponseDto;
import com.example.petback.reservation.entity.Reservation;
import com.example.petback.review.dto.ReviewResponseDto;
import com.example.petback.species.SpeciesEnum;
import com.example.petback.subject.SubjectEnum;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
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
    private List<ReviewResponseDto> reviews;

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
                .build();
    }
}
