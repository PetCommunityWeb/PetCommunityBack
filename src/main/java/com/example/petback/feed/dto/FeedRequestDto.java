package com.example.petback.feed.dto;

import com.example.petback.feed.entity.Feed;
import lombok.Getter;

@Getter
public class FeedRequestDto {

    private Long id;
    private String title;
    private String content;

    // 이건 어디에쓸까요?
    public Feed toEntity() {
        return Feed.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
    }
}
