package com.example.petback.like.dto;

import com.example.petback.like.entity.Like;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LikeRequestDto {

    private Long userId;
    private Long feedId;

    public Like toEntity() {
        return Like.builder()
                .user(userId)
                .feedId(feedId)
                .build()
    }
}
