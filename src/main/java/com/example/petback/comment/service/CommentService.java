package com.example.petback.comment.service;

import com.example.petback.comment.dto.CommentRequestDto;
import com.example.petback.comment.dto.CommentResponseDto;
import com.example.petback.user.entity.User;

import java.util.List;

public interface CommentService {

    // 코멘트 작성
    CommentResponseDto createComment(CommentRequestDto requestDto, Long id, User user);

    // 코멘트 수정
    CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, User user);

    // 코멘트 삭제
    void deleteComment(Long id, User user);

}
