package com.example.petback.comment.service;

import com.example.petback.comment.dto.CommentRequestDto;
import com.example.petback.comment.dto.CommentResponseDto;
import com.example.petback.comment.entity.Comment;
import com.example.petback.comment.repository.CommentRepository;
import com.example.petback.feed.entity.Feed;
import com.example.petback.feed.repository.FeedRepository;
import com.example.petback.feed.service.FeedService;
import com.example.petback.user.entity.User;
import com.example.petback.user.enums.UserRoleEnum;
import com.example.petback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final FeedService feedService;

    // 코멘트 생성
    @Override
    public CommentResponseDto createComment(CommentRequestDto requestDto, Long id, User user) {
        Feed feed = feedService.findFeed(id);
        Comment comment = requestDto.toEntity(feed, user);
        commentRepository.save(comment);
        return CommentResponseDto.of(comment);
    }

    // 코멘트 수정
    @Override
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, User user) {
        String username = findComment(id).getUser().getUsername();
        Comment comment = findComment(id);
        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || username.equals(user.getUsername()))) {
            throw new RejectedExecutionException();
        }
        comment.setContent(requestDto.getContent());
        return CommentResponseDto.of(comment);

    }

    //코멘트 삭제
    @Override
    public void deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow();

        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUser().equals(user)) {
            throw new RejectedExecutionException();
        }
        commentRepository.delete(comment);
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("코멘트가 존재하지 않습니다."));

    }

}