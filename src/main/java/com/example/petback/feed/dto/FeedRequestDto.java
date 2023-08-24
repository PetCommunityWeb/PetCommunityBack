package com.example.petback.feed.dto;

import com.example.petback.feed.entity.Feed;
import com.example.petback.user.entity.User;
import lombok.Getter;

@Getter
public class FeedRequestDto {

    private String title;
    private String content;
    private String imageUrl;

    public Feed toEntity(User user) {
        return Feed.builder()
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .user(user)
                .build();
    }
}
