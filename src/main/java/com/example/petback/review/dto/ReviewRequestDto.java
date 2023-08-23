package com.example.petback.review.dto;

import com.example.petback.reservation.entity.Reservation;
import com.example.petback.review.entity.Review;
import com.example.petback.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ReviewRequestDto {
    private String reservationNum;
    private String title;
    private String content;
    private int rate;
    private String imageUrl;

    public Review toEntity(Reservation reservation, User user) {
        return Review.builder()
                .reservation(reservation)
                .user(user)
                .title(title)
                .content(content)
                .rate(rate)
                .imageUrl(imageUrl)
                .build();
    }
}
