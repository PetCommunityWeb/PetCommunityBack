package com.example.petback.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ReviewListResponseDto {
    private List<ReviewResponseDto> reviewResponseDtos;
}
