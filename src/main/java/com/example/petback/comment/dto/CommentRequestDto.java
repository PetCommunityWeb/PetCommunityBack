package com.example.petback.comment.dto;

import com.example.petback.comment.entity.Comment;
import com.example.petback.feed.entity.Feed;
import com.example.petback.user.entity.User;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    private String content;

    public Comment toEntity(Feed feed, User user) {
        return Comment.builder()
                .content(content)
                .feed(feed)
                .user(user)
                .build();
    }
}
