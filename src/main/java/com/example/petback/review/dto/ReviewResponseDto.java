package com.example.petback.review.dto;

import com.example.petback.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDto {
    private Long id;
    private String title;
    private String content;
    private int rate;
    private String imageUrl;

    public static ReviewResponseDto of(Review review) {
        if (review == null) return null;
        return ReviewResponseDto.builder()
                .id((review.getId()))
                .title(review.getTitle())
                .content(review.getContent())
                .rate(review.getRate())
                .imageUrl(review.getImageUrl())
                .build();
    }
}
