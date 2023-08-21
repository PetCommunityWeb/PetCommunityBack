package com.example.petback.tip.dto;

import com.example.petback.tip.entity.Tip;
import lombok.Getter;

@Getter
public class TipRequestDto {

    private Long id;
    private String title;
    private String content;

    public Tip toEntity() {
        return Tip.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
    }


}
