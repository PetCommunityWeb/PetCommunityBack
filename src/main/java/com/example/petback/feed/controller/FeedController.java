package com.example.petback.feed.controller;

import com.example.petback.common.advice.ApiResponseDto;
import com.example.petback.common.security.UserDetailsImpl;
import com.example.petback.feed.dto.FeedRequestDto;
import com.example.petback.feed.dto.FeedResponseDto;
import com.example.petback.feed.service.FeedService;
import com.example.petback.user.service.UserService;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds")
public class FeedController {
    private final FeedService feedService;
    private final UserService userService;

    //피드 생성
    @PostMapping("/")
    public FeedResponseDto createFeed(@RequestBody FeedRequestDto requestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return feedService.createFeed(requestDto, userDetails.getUser());
    }

    //피드 전체 조회
    @GetMapping("/")
    public ResponseEntity selectFeeds() {
        List<FeedResponseDto> responseDtos = feedService.selectFeeds();
        return ResponseEntity.ok().body(responseDtos);
    }

    //피드 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity selectFeed(@PathVariable Long id) {
        FeedResponseDto responseDto = feedService.selectFeed(id);
        return ResponseEntity.ok().body(responseDto);
    }

    //피드 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto> updateFeed(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long id,
                                                     @RequestBody FeedRequestDto requestDto) {
        try {
            FeedResponseDto result = feedService.updateFeed(id, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("수정이 완료되었습니다", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 수정할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }

    }
    //피드 삭제
    // softDelete 함 (Feed Entity 참고)

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteFeed(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long id) {
        try {
            feedService.deleteFeed(id, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("삭제되었습니다.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException c) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 피드 좋아요
    @PostMapping("/{id}/likes")
    public ResponseEntity<ApiResponseDto> likeFeed(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable Long id){
        try{
            feedService.likeFeed(id, userDetails.getUser());
        }catch (DuplicateRequestException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponseDto("게시글 좋아요 성공",HttpStatus.ACCEPTED.value()));
    }

    @DeleteMapping("/{id}/likes")
    public ResponseEntity<ApiResponseDto> dislikeFeed(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @PathVariable Long id) {
        try {
            feedService.dislikeFeed(id, userDetails.getUser());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponseDto("게시글 좋아요 취소 성공", HttpStatus.ACCEPTED.value()));
    }
}
