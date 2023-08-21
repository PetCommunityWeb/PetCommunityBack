package com.example.petback.comment.controller;

import com.example.petback.comment.dto.CommentRequestDto;
import com.example.petback.comment.dto.CommentResponseDto;
import com.example.petback.comment.service.CommentService;
import com.example.petback.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService  commentService;

    // 코멘트 생성
    @PostMapping("/")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto,
                                            @RequestParam Long id,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(commentRequestDto, userDetails.getUser(),id);
    }

//    // 코멘트 조회
//
//    @GetMapping("/")
//    public
}
