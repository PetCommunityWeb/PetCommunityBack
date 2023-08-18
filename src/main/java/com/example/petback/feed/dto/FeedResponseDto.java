package com.example.petback.feed.dto;

import com.example.petback.feed.entity.Feed;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FeedResponseDto {

    private Long id;
    private String title;
    private String content;

    public static FeedResponseDto of(Feed feed) {
        return FeedResponseDto.builder()
                .id(feed.getId())
                .title(feed.getTitle())
                .content(feed.getContent())
                .build();
    }
}
