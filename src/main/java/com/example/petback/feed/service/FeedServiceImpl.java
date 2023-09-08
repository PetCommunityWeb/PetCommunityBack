package com.example.petback.feed.service;

import com.example.petback.feed.dto.FeedListResponseDto;
import com.example.petback.feed.dto.FeedRequestDto;
import com.example.petback.feed.dto.FeedResponseDto;
import com.example.petback.feed.entity.Feed;
import com.example.petback.feed.repository.FeedLikeRepository;
import com.example.petback.feed.repository.FeedRepository;
import com.example.petback.feed.entity.FeedLike;
import com.example.petback.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedServiceImpl implements FeedService {
    private final FeedRepository feedRepository;
    private final FeedLikeRepository feedLikeRepository;

    // 피드 생성
    @Override
    @Caching(evict = {
            @CacheEvict(value = "myFeeds", key = "#user.id"),
            @CacheEvict(value = "allFeeds", allEntries = true)
    })
    public FeedResponseDto createFeed(FeedRequestDto requestDto, User user) {
        Feed feed = requestDto.toEntity(user);
        feedRepository.save(feed);
        return FeedResponseDto.of(feed);
    }

    // 피드 전체 조회
    @Transactional(readOnly = true)
    @Override
    @Cacheable(value = "allFeeds")
    public FeedListResponseDto selectFeeds() {
        return FeedListResponseDto.builder()
                .feedResponseDtos(feedRepository.findAll().stream().map(FeedResponseDto::of).toList())
                .build();
    }

    //내가 쓴 피드만 조회
    @Transactional
    @Override
    @Cacheable(value = "myFeeds", key = "#user.id")
    public FeedListResponseDto selectFeedsByUser(User user) {

        return FeedListResponseDto.builder()
                .feedResponseDtos(feedRepository.findByUser(user).stream().map(FeedResponseDto::of).toList())
                .build();
    }


    // 피드 상세 조회
    @Transactional(readOnly = true)
    @Override
    @Cacheable(value = "feed")
    public FeedResponseDto selectFeed(Long id) {
        Feed feed = findFeed(id);
        return FeedResponseDto.of(feed);
    }

    // 피드 수정
    @Override
    @Caching(evict = {
            @CacheEvict(value = "feed", key = "#id"),
            @CacheEvict(value = "myFeeds", key = "#user.id"),
            @CacheEvict(value = "allFeeds", allEntries = true)
    })
    public void updateFeed(Long id, FeedRequestDto requestDto, User user) {
        Feed feed = findFeed(id);
        if (!feed.getUser().equals(user)) {
            throw new IllegalArgumentException("피드 작성자만 수정할 수 있습니다.");
        }
        feed.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getImageUrl());
    }

    // 피드 삭제
    @Override
    @Caching(evict = {
            @CacheEvict(value = "myFeeds", key = "#user.id"),
            @CacheEvict(value = "allFeeds", allEntries = true)
    })
    public void deleteFeed(Long id, User user) {
        Feed feed = findFeed(id);
        if (!feed.getUser().equals(user)) {
            throw new IllegalArgumentException("피드 작성자만 삭제할 수 있습니다.");
        }
        feedRepository.delete(feed);
    }
    
    // 피드 좋아요
    @Override
    @Caching(evict = {
            @CacheEvict(value = "allFeeds", allEntries = true),
            @CacheEvict(value = "feed", key = "#id"),
            @CacheEvict(value = "myFeeds", key = "#user.id"),
    })
    public String likeFeed(Long id, User user) {
        Feed feed = findFeed(id);
        if (feed.getUser().equals(user)) {
            throw new IllegalArgumentException("본인 피드에 좋아요 누를 수 없습니다.");
        }
        if (feedLikeRepository.existsByUserAndFeed(user, feed)) {
            FeedLike feedLike = feedLikeRepository.findByUserAndFeed(user, feed).get();
            feedLikeRepository.delete(feedLike);
            return "취소";
        }
        FeedLike feedLike = FeedLike.builder()
                .user(user)
                .feed(feed)
                .build();
        feedLikeRepository.save(feedLike);
        return "성공";
    }

    public Feed findFeed(Long id) {
        return feedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("피드가 존재하지 않습니다."));
    }


}
