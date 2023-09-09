package com.example.petback.tip.dto;

import com.example.petback.tip.entity.Tip;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipResponseDto {

    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private String username;
    private int likeCount;


    public static TipResponseDto of(Tip tip) {
        return TipResponseDto.builder()
                .id(tip.getId())
                .title(tip.getTitle())
                .content(tip.getContent())
                .imageUrl(tip.getImageUrl())
                .likeCount(tip.getTipLikes().size())
                .username(tip.getUser().getUsername())
                .build();

    }
}
