package com.example.petback.feed.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedListResponseDto {
    private List<FeedResponseDto> feedResponseDtos;
}
