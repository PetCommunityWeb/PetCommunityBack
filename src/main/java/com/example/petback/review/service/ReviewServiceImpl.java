package com.example.petback.review.service;

import com.example.petback.reservation.entity.Reservation;
import com.example.petback.reservation.repository.ReservationRepository;
import com.example.petback.review.dto.ReviewListResponseDto;
import com.example.petback.review.dto.ReviewRequestDto;
import com.example.petback.review.dto.ReviewResponseDto;
import com.example.petback.review.entity.Review;
import com.example.petback.review.repository.ReviewRepository;
import com.example.petback.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Caching(evict = {
            @CacheEvict(value = "myHospitals", key = "#user.id"),
            @CacheEvict(value = "myReservations", key = "#user.id"),
            @CacheEvict(value = "myReviews", key = "#user.id")
    })
    public ReviewResponseDto createReview(User user, ReviewRequestDto requestDto) {
        String reservationNum = requestDto.getReservationNum();
        Reservation reservation = reservationRepository.findById(reservationNum).orElseThrow(() -> new IllegalArgumentException("해당 예약이 없습니다."));
        if (!reservation.getUser().equals(user)) throw new IllegalArgumentException("리뷰작성 권한이 없습니다.");
        Review review = requestDto.toEntity(reservation, user);
        reviewRepository.save(review);
        return ReviewResponseDto.of(review);
    }

    @Override
    @Cacheable(value = "myReviews", key = "#user.id")
    public ReviewListResponseDto selectAllReviews(User user) {
        List<Review> reviews = reviewRepository.findAllByUser(user);
        return ReviewListResponseDto.builder()
                .reviewResponseDtos(reviews.stream().map(ReviewResponseDto::of).toList())
                .build();
    }

    @Override
    @Cacheable(value = "review", key="#id")
    public ReviewResponseDto selectReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));
        return ReviewResponseDto.of(review);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "myHospitals", key = "#user.id"),
            @CacheEvict(value = "myReservations", key = "#user.id"),
            @CacheEvict(value = "myReviews", key = "#user.id"),
            @CacheEvict(value = "review", key = "#id")
    })
    public ReviewResponseDto updateReview(User user, Long id, ReviewRequestDto requestDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));
        if (!review.getUser().equals(user)) throw new IllegalArgumentException("수정 권한이 없습니다.");
        review.updateTitle(requestDto.getTitle())
                .updateContent(requestDto.getContent())
                .updateRate(requestDto.getRate())
                .updateImageUrl(requestDto.getImageUrl());
        return ReviewResponseDto.of(review);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "myHospitals", key = "#user.id"),
            @CacheEvict(value = "myReservations", key = "#user.id"),
            @CacheEvict(value = "myReviews", key = "#user.id")
    })
    public void deleteReview(User user, Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));
        if (!review.getUser().equals(user)) throw new IllegalArgumentException("삭제 권한이 없습니다.");
        reviewRepository.delete(review);
    }
}
