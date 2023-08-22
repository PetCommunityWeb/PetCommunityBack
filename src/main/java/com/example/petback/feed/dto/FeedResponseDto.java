package com.example.petback.feed.dto;

import com.example.petback.comment.dto.CommentRequestDto;
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
    private List<CommentResponseDto> comments;

    public static FeedResponseDto of(Feed feed) {
        return FeedResponseDto.builder()
                .id(feed.getId())
                .title(feed.getTitle())
                .content(feed.getContent())
                .comments(feed.getComments().stream().map(CommentResponseDto::of).toList())
                .build();
    }
}
