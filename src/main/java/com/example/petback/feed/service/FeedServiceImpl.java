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

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    //피드 생성
    @Override
    public FeedResponseDto createFeed(FeedRequestDto requestDto, User user) {
        Feed feed = feedRepository.save(new Feed(requestDto, user));
        return new FeedResponseDto(feed);
    }

    //피드 전체 조회
    @Override
    public List<FeedResponseDto> selectFeeds() {
        return feedRepository.findAll().stream().map(FeedResponseDto::new).toList();
    }

    //피드 상세 조회
    @Override
    public Feed selectFeed(Long id) {
        return feedRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 피드가 존재하지 않습니다."));
    }

    //피드 수정
    @Override
    public FeedResponseDto updateFeed(Long id, FeedRequestDto requestDto, User user) {
        String username = selectFeed(id).getUser().getUsername();
        Feed feed = selectFeed(id);
        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || username.equals(user.getUsername()))) {
            throw new RejectedExecutionException();
        }
        feed.setTitle(requestDto.getTitle());
        feed.setContent(requestDto.getContent());
        return new FeedResponseDto(feed);
    }

    //피드 삭제
    @Override
    public void deleteFeed(Long id, User user) {
        String username = selectFeed(id).getUser().getUsername();
        Feed feed = selectFeed(id);
        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || username.equals(user.getUsername()))) {
            throw new RejectedExecutionException();
        } else feedRepository.delete(feed);
    }


}
