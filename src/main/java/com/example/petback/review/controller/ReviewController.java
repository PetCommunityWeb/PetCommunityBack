package com.example.petback.review.controller;

import com.example.petback.common.security.UserDetailsImpl;
import com.example.petback.review.dto.ReviewRequestDto;
import com.example.petback.review.dto.ReviewResponseDto;
import com.example.petback.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity createReview(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ReviewRequestDto requestDto){
        ReviewResponseDto responseDto = reviewService.createReview(userDetails.getUser(), requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping
    public ResponseEntity selectAllReviews(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<ReviewResponseDto> responseDtos = reviewService.selectAllReviews(userDetails.getUser()).getReviewResponseDtos();
        return ResponseEntity.ok().body(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity selectReview(@PathVariable Long id){
        ReviewResponseDto responseDto = reviewService.selectReview(id);
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateReview(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody ReviewRequestDto requestDto){
        ReviewResponseDto responseDto = reviewService.updateReview(userDetails.getUser(), id, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReview(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id){
        reviewService.deleteReview(userDetails.getUser(), id);
        return ResponseEntity.ok().body("리뷰 삭제가 완료되었습니다.");
    }
}
