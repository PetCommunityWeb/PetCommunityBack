package com.example.petback.comment.dto;

import com.example.petback.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    private Long id;
    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .build();
    }
}
