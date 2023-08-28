package com.example.petback.tip.controller;

import com.example.petback.common.advice.ApiResponseDto;
import com.example.petback.common.security.UserDetailsImpl;
import com.example.petback.tip.dto.TipRequestDto;
import com.example.petback.tip.dto.TipResponseDto;
import com.example.petback.tip.service.TipService;
import com.example.petback.user.service.UserService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tips")
public class TipContorller {
    private final TipService tipService;
    private final UserService userService;

    // 팁 작성
    @PostMapping("")
    public TipResponseDto createTip(@RequestBody TipRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return tipService.createTip(requestDto, userDetails.getUser());
    }

    // 팁 전체 조회
    @GetMapping("")
    public ResponseEntity selectTips() {
        List<TipResponseDto> responseDto = tipService.selectTips();
        return ResponseEntity.ok().body(responseDto);
    }

    // 팁 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity selectTip(@PathVariable Long id) {
        TipResponseDto responseDto = tipService.selectTip(id);
        return ResponseEntity.ok().body(responseDto);
    }

    // 팁 검색어 조회


    // 팁 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto> updateTip(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody TipRequestDto requestDto) {
        try {
            TipResponseDto result = tipService.updateTip(id, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("수정이 완료되었습니다.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 수정할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 팁 삭제

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteTip(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            tipService.deleteTip(id, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("삭제되었습니다.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException c) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 팁 좋아요
    @PostMapping("/{id}/likes")
    public ResponseEntity<ApiResponseDto> likeTip(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            tipService.likeTip(userDetails, id);
        } catch (DuplicateRequestException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("팁 좋아요 성공", HttpStatus.OK.value()));
    }

    // 팁 좋아요 취소
    @DeleteMapping("/{id}/likes")
    public ResponseEntity<ApiResponseDto> deleteLikeTip(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            tipService.deleteLikeTip(userDetails, id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto("팁 좋아요 취소 성공", HttpStatus.OK.value()));
    }
}
