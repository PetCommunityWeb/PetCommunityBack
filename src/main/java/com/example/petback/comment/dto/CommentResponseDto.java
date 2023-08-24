package com.example.petback.comment.dto;

import com.example.petback.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponseDto {

    private Long id;
    private String username;
    private String content;

    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .username(comment.getUsername())
                .content(comment.getContent())
                .build();

    }
}
