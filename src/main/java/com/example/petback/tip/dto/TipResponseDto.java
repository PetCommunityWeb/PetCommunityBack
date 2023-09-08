package com.example.petback.tip.dto;

import com.example.petback.tip.entity.Tip;
import com.example.petback.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TipResponseDto {

    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private String username;
    private User user;
    private int likeCount;


    public static TipResponseDto of(Tip tip) {
        return TipResponseDto.builder()
                .id(tip.getId())
                .title(tip.getTitle())
                .content(tip.getContent())
                .imageUrl(tip.getImageUrl())
                .likeCount(tip.getTipLikes().size())
                .user(tip.getUser())
                .username(tip.getUser().getUsername())
                .build();

    }
}
