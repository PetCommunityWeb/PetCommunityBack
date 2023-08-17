package com.example.petback.feed.dto;

import com.example.petback.feed.entity.Feed;

public class FeedResponseDto {

    private Long id;
    private String title;
    private String content;

    public FeedResponseDto(Feed feed) {
        this.id = feed.getId();
        this.title = feed.getTitle();
        this.content = feed.getContent();
    }
}
