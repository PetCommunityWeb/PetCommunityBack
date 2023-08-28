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

    // 댓글 생성
    @Override
    public CommentResponseDto createComment(CommentRequestDto requestDto, Long id, User user) {
        Feed feed = feedService.findFeed(id);
        Comment comment = requestDto.toEntity(feed, user);
        commentRepository.save(comment);
        return CommentResponseDto.of(comment);
    }

    // 댓글 수정
    @Override
    public void updateComment(Long id, CommentRequestDto requestDto, User user) {
        Comment comment = findComment(id);
        if (!comment.getUser().equals(user)) {
            throw new IllegalArgumentException("댓글 작성자만 수정할 수 있습니다.");
        }
        comment.updateContent(requestDto.getContent());
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Long id, User user) {
        Comment comment = findComment(id);
        if (!comment.getUser().equals(user)) {
            throw new IllegalArgumentException("댓글 작성자만 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

    }

}