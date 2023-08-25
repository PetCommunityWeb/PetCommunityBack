package com.example.petback.review.service;

import com.example.petback.reservation.entity.Reservation;
import com.example.petback.reservation.repository.ReservationRepository;
import com.example.petback.review.dto.ReviewRequestDto;
import com.example.petback.review.dto.ReviewResponseDto;
import com.example.petback.review.entity.Review;
import com.example.petback.review.repository.ReviewRepository;
import com.example.petback.user.entity.User;
import lombok.RequiredArgsConstructor;
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
    public ReviewResponseDto createReview(User user, ReviewRequestDto requestDto) {
        String reservationNum = requestDto.getReservationNum();
        Reservation reservation = reservationRepository.findById(reservationNum).orElseThrow(() -> new IllegalArgumentException("해당 예약이 없습니다."));
        if (!reservation.getUser().equals(user)) throw new IllegalArgumentException("리뷰작성 권한이 없습니다.");
        Review review = requestDto.toEntity(reservation, user);
        reviewRepository.save(review);
        return ReviewResponseDto.of(review);
    }

    @Override
    public List<ReviewResponseDto> selectAllReviews(User user) {
        List<Review> reviews = reviewRepository.findAllByUser(user);
        return reviews.stream().map(ReviewResponseDto::of).toList();
    }

    @Override
    public ReviewResponseDto selectReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));
        return ReviewResponseDto.of(review);
    }

    @Override
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
    public void deleteReview(User user, Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));
        if (!review.getUser().equals(user)) throw new IllegalArgumentException("삭제 권한이 없습니다.");
        reviewRepository.delete(review);
    }
}
