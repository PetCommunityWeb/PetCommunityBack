package com.example.petback.tip.dto;

import com.example.petback.tip.entity.Tip;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TipResponseDto {

    private Long id;
    private String title;
    private String content;

    public static TipResponseDto of(Tip tip) {
        return TipResponseDto.builder()
                .id(tip.getId())
                .title(tip.getTitle())
                .content(tip.getContent())
                .build();

    }
}
