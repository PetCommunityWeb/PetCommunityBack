package com.example.petback.tip.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TipListResponseDto {
    private final List<TipResponseDto> tipsList;

    public TipListResponseDto(List<TipResponseDto> tipList) {
        this.tipsList = tipList;
    }
}
