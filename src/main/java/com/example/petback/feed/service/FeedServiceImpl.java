package com.example.petback.feed.service;

import com.example.petback.feed.dto.FeedRequestDto;
import com.example.petback.feed.dto.FeedResponseDto;
import com.example.petback.feed.entity.Feed;
import com.example.petback.feed.repository.FeedRepository;
import com.example.petback.user.entity.User;
import com.example.petback.user.enums.UserRoleEnum;
import com.example.petback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedServiceImpl implements FeedService {
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    //피드 생성
    @Override
    public FeedResponseDto createFeed(FeedRequestDto requestDto, User user) {
        Feed feed = requestDto.toEntity();
        feed.setUser(user);
        feedRepository.save(feed);
        return FeedResponseDto.of(feed);
    }

    //피드 전체 조회
    @Transactional(readOnly = true)
//    @Override
    public List<FeedResponseDto> selectFeeds() {
        return feedRepository.findAll().stream().map(FeedResponseDto::of).toList();
    }

    //피드 상세 조회
    @Transactional(readOnly = true)
//    @Override
    public FeedResponseDto selectFeed(Long id) {
        Feed feed = findFeed(id);
        return FeedResponseDto.of(feed);
    }

    //피드 수정
//    @Override
    public FeedResponseDto updateFeed(Long id, FeedRequestDto requestDto, User user) {
        String username = findFeed(id).getUser().getUsername();
        Feed feed = findFeed(id);
        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || username.equals(user.getUsername()))) {
            throw new RejectedExecutionException();
        }
        // @setter말고 따로 set메서드를 만드는 것이 좋은가?
        feed.setTitle(requestDto.getTitle());
        feed.setContent(requestDto.getContent());
        return FeedResponseDto.of(feed);
    }

    //피드 삭제
//    @Override
    public void deleteFeed(Long id, User user) {
        String username = findFeed(id).getUser().getUsername();
        Feed feed = findFeed(id);
        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || username.equals(user.getUsername()))) {
            throw new RejectedExecutionException();
        } else feedRepository.delete(feed);
    }

    private Feed findFeed(Long id) {
        return feedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("피드가 존재하지 않습니다."));
    }


}
