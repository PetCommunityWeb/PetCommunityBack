package com.example.petback.tip.dto;

import com.example.petback.tip.entity.Tip;
import com.example.petback.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TipResponseDto {

    private Long id;
    private String title;
    private String username;
    private String content;
    private String imageUrl;
    private int likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String customLocalDateTimeFormat;


    public static TipResponseDto of(Tip tip) {

        return TipResponseDto.builder()
                .id(tip.getId())
                .title(tip.getTitle())
                .username(tip.getUser().getUsername())
                .content(tip.getContent())
                .imageUrl(tip.getImageUrl())
                .likeCount(tip.getTipLikes().size())
                .createdAt(tip.getCreatedAt())
                .modifiedAt(tip.getModifiedAt())
                .customLocalDateTimeFormat(tip.getCustomLocalDateTimeFormat())
                .build();

    }
}
