package com.example.petback.feed.dto;

import com.example.petback.comment.dto.CommentResponseDto;
import com.example.petback.feed.entity.Feed;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FeedResponseDto {

    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private String username;
    private List<CommentResponseDto> comments;
    private int likeCount;
    private int commentCount;

    public static FeedResponseDto of(Feed feed) {
        return FeedResponseDto.builder()
                .id(feed.getId())
                .title(feed.getTitle())
                .content(feed.getContent())
                .imageUrl(feed.getImageUrl())
                .username(feed.getUser().getUsername())
                .comments(feed.getComments().stream().map(CommentResponseDto::of).toList())
                .likeCount(feed.getFeedLikes().size())
                .commentCount(feed.getComments().size())
                .build();
    }
}
