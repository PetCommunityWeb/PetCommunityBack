package com.example.petback.feed.service;

import com.example.petback.feed.dto.FeedRequestDto;
import com.example.petback.feed.dto.FeedResponseDto;
import com.example.petback.feed.entity.Feed;
import com.example.petback.user.entity.User;

import java.util.List;

public interface FeedService {

    //생성

    FeedResponseDto createFeed(FeedRequestDto requestDto, User user);

    //전체 조회
    List<FeedResponseDto> selectFeeds();
    //상세 조회
    Feed selectFeed(Long id);
    //수정
    FeedResponseDto updateFeed(Long id, FeedRequestDto requestDto, User user);

    //삭제
    void deleteFeed(Long id, User user);


}
