package com.example.petback.feed.service;

import com.example.petback.common.advice.ApiResponseDto;
import com.example.petback.feed.dto.FeedRequestDto;
import com.example.petback.feed.dto.FeedResponseDto;
import com.example.petback.user.entity.User;

import java.util.List;

public interface FeedService {

    //생성
    ApiResponseDto createFeed(FeedRequestDto requestDto, User user);
    //전체 조회
    List<FeedResponseDto> searchFeeds();
    //상세 조회


    //수정
    //삭제


}
