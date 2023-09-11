package com.example.petback.comment.dto;

import com.example.petback.comment.entity.Comment;
import com.example.petback.feed.entity.Feed;
import com.example.petback.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
