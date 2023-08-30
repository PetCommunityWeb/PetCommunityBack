package com.example.petback.feed.controller;

import com.example.petback.common.advice.ApiResponseDto;
import com.example.petback.common.security.UserDetailsImpl;
import com.example.petback.feed.dto.FeedRequestDto;
import com.example.petback.feed.dto.FeedResponseDto;
import com.example.petback.feed.service.FeedService;
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

    //피드 생성
    @PostMapping
    public ResponseEntity createFeed(@RequestBody FeedRequestDto requestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        FeedResponseDto responseDto = feedService.createFeed(requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    //피드 전체 조회
    @GetMapping
    public ResponseEntity selectFeeds() {
        List<FeedResponseDto> responseDtos = feedService.selectFeeds();
        return ResponseEntity.ok().body(responseDtos);
    }
    //내가 쓴 피드 조회
    @GetMapping("/my-feeds")
    public ResponseEntity selectFeeds(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<FeedResponseDto> responseDtos = feedService.selectFeedsByUser(userDetails.getUser());
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
    public ResponseEntity updateFeed(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @PathVariable Long id,
                                                     @RequestBody FeedRequestDto requestDto) {
        feedService.updateFeed(id, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("피드가 수정되었습니다.", HttpStatus.OK.value()));
    }

    //피드 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity deleteFeed(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        feedService.deleteFeed(id, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("피드가 삭제되었습니다.", HttpStatus.OK.value()));
    }

    // 피드 좋아요
    @PostMapping("/{id}/likes")
    public ResponseEntity likeFeed(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id){
        String result = feedService.likeFeed(id, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto(result, HttpStatus.OK.value()));
    }
}
