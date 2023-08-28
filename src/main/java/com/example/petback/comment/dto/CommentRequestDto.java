package com.example.petback.comment.dto;

import com.example.petback.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    private Long feedId;
    private String username;
    private String content;

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .username(username)
                .build();
    }
}
