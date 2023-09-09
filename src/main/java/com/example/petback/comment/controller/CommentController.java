package com.example.petback.comment.controller;

import com.example.petback.comment.dto.CommentRequestDto;
import com.example.petback.comment.dto.CommentResponseDto;
import com.example.petback.comment.service.CommentService;
import com.example.petback.common.advice.ApiResponseDto;
import com.example.petback.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService  commentService;

    // 댓글 생성
    @PostMapping("/{id}")
    public ResponseEntity createComment(@RequestBody CommentRequestDto requestDto,
                                        @PathVariable Long id,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto commentResponseDto = commentService.createComment(requestDto, id, userDetails.getUser());
        return ResponseEntity.ok().body(commentResponseDto);
    }


    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long id,
                                                        @RequestBody CommentRequestDto requestDto) {
        commentService.updateComment(id, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("댓글이 수정 되었습니다.", HttpStatus.OK.value()));
    }
    
    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long id) {
        commentService.deleteComment(id, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("댓글이 삭제 되었습니다." , HttpStatus.OK.value()));
    }
}
