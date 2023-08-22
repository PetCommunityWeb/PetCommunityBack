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
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService  commentService;

    // 코멘트 생성
    @PostMapping("/")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto,
                                            @RequestParam Long feedId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(commentRequestDto, userDetails.getUser(),feedId);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long id,
                                                        @RequestBody CommentRequestDto requestDto) {
        try {
            CommentResponseDto result = commentService.updateComment(id, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("수정이 완료되었습니다", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 수정할 수 있습니다", HttpStatus.BAD_REQUEST.value()));

        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @PathVariable Long commentId,
                                                        @RequestParam Long feedId){
        try{
            commentService.deleteComment(commentId, feedId, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("삭제 되었습니다." , HttpStatus.OK.value()));
        } catch (RejectedExecutionException c) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }
}
