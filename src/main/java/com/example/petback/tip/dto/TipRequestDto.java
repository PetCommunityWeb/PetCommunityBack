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
public class TipRequestDto {

    private Long id;
    private String title;
    private String content;
    private String imageUrl;

    public Tip toEntity() {
        return Tip.builder()
                .id(id)
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .build();
    }



}
