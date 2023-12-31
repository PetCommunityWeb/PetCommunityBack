package com.example.petback.feed.service;

import com.example.petback.feed.dto.FeedListResponseDto;
import com.example.petback.feed.dto.FeedRequestDto;
import com.example.petback.feed.dto.FeedResponseDto;
import com.example.petback.feed.entity.Feed;
import com.example.petback.user.entity.User;

public interface FeedService {

    //피드 생성
    FeedResponseDto createFeed(FeedRequestDto requestDto, User user);

    //전체 조회
    FeedListResponseDto selectFeeds();

    //상세 조회
    FeedResponseDto selectFeed(Long id);

    //수정
    void updateFeed(Long id, FeedRequestDto requestDto, User user);

    //삭제
    void deleteFeed(Long id, User user);

    Feed findFeed(Long id);

    String likeFeed(Long id, User user);

    FeedListResponseDto selectFeedsByUser(User user);

    FeedListResponseDto selectFeedsByLike();

    FeedListResponseDto selectFeedsByComment();
}
