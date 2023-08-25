package com.example.petback.feed.service;

import com.example.petback.feed.dto.FeedRequestDto;
import com.example.petback.feed.dto.FeedResponseDto;
import com.example.petback.feed.entity.Feed;
import com.example.petback.feed.repository.FeedLikeRepository;
import com.example.petback.feed.repository.FeedRepository;
import com.example.petback.feed.entity.FeedLike;
import com.example.petback.user.entity.User;
import com.example.petback.user.enums.UserRoleEnum;
import com.example.petback.user.repository.UserRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedServiceImpl implements FeedService {
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;
    private final FeedLikeRepository feedLikeRepository;

    //피드 생성
    @Override
    public FeedResponseDto createFeed(FeedRequestDto requestDto, User user) {
        Feed feed = requestDto.toEntity();
        feed.setUser(user);
        feedRepository.save(feed);
//        return FeedResponseDto.of(feed);
        return FeedResponseDto.builder()
                .id(feed.getId())
                .title(feed.getTitle())
                .content(feed.getContent())
                .build();
    }

    //피드 전체 조회
    @Transactional(readOnly = true)
    @Override
    public List<FeedResponseDto> selectFeeds() {
        return feedRepository.findAll().stream().map(FeedResponseDto::of).toList();
    }

    //피드 상세 조회
    @Transactional(readOnly = true)
    @Override
    public FeedResponseDto selectFeed(Long id) {
        Feed feed = findFeed(id);
        return FeedResponseDto.of(feed);
    }

    //피드 수정
    @Override
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
    @Override
    public void deleteFeed(Long id, User user) {
        String username = findFeed(id).getUser().getUsername();
        Feed feed = findFeed(id);
        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || username.equals(user.getUsername()))) {
            throw new RejectedExecutionException();
        } else feedRepository.delete(feed);
    }

    public void likeFeed(Long id, User user) {
        Feed feed = findFeed(id);
        if (feedLikeRepository.existsByUserAndFeed(user, feed)) {
            throw new DuplicateRequestException("이미 좋아요 한 피드 입니다.");
        } else {
            FeedLike feedLike = new FeedLike(user, feed);
            feedLikeRepository.save(feedLike);
        }
    }

    public void dislikeFeed(Long id, User user) {
        Feed feed = findFeed(id);
        Optional<FeedLike> feedLikeOptional = feedLikeRepository.findByUserAndFeed(user, feed);
        if(feedLikeOptional.isPresent()) {
            feedLikeRepository.delete(feedLikeOptional.get());
        }else {
            throw new IllegalArgumentException("해당 게시글에 취소할 수 있는 좋아요가 없습니다.");
        }
    }

    public Feed findFeed(Long id) {
        return feedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("피드가 존재하지 않습니다."));
    }


}
