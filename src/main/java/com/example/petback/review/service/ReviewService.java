package com.example.petback.review.service;

import com.example.petback.review.dto.ReviewListResponseDto;
import com.example.petback.review.dto.ReviewRequestDto;
import com.example.petback.review.dto.ReviewResponseDto;
import com.example.petback.user.entity.User;

public interface ReviewService {
    ReviewResponseDto createReview(User user, ReviewRequestDto requestDto);

    ReviewListResponseDto selectAllReviews(User user);

    ReviewResponseDto selectReview(Long id);

    ReviewResponseDto updateReview(User user, Long id, ReviewRequestDto requestDto);

    void deleteReview(User user, Long id);
}
