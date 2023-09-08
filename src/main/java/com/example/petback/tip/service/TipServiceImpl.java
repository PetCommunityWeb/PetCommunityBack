package com.example.petback.tip.service;


import com.example.petback.common.security.UserDetailsImpl;
import com.example.petback.feed.entity.Feed;
import com.example.petback.tip.dto.TipRequestDto;
import com.example.petback.tip.dto.TipResponseDto;
import com.example.petback.tip.entity.Tip;
import com.example.petback.tip.entity.TipLike;
import com.example.petback.tip.repository.TipLikeRepository;
import com.example.petback.tip.repository.TipRepository;
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
public class TipServiceImpl implements TipService {
    private final TipRepository tipRepository;
    private final TipLikeRepository tipLikeRepository;

    // 팁 작성
    @Override
    public TipResponseDto createTip(TipRequestDto requestDto, User user) {
        Tip tip = requestDto.toEntity();
        tip.setUser(user);
        tipRepository.save(tip);

        return TipResponseDto.of(tip);
    }

    // 팁 전체 조회
    @Transactional(readOnly = true)
    @Override
    public List<TipResponseDto> selectTips() {
        return tipRepository.findAll().stream().map(TipResponseDto::of).toList();
    }

    // 팁 상세 조회
    @Override
    @Transactional(readOnly = true)
    public TipResponseDto selectTip(Long id) {
        Tip tip = findTip(id);
        return TipResponseDto.of(tip);
    }

    // 특정 팁 찾기
    private Tip findTip(Long id) {
        return tipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    // 키워드로 팁 검색 -> 시간 남으면 해보기



    // 팁 수정
    @Override
    public TipResponseDto updateTip(Long id, TipRequestDto requestDto, User user) {
        String username = findTip(id).getUser().getUsername();
        Tip tip = findTip(id);
        if (!(user.getRole().equals(UserRoleEnum.ADMIN) || username.equals(user.getUsername()))) {
            throw new IllegalArgumentException("팁 작성자만 수정할 수 있습니다.");
        }

        tip.setTitle(requestDto.getTitle());
        tip.setContent(requestDto.getContent());
        tip.setImageUrl(requestDto.getImageUrl());

        return TipResponseDto.of(tip);
    }

    // 팁 삭제
    @Override
    public void deleteTip(Long id, User user) {
        Tip tip = findTip(id);
        if (!tip.getUser().equals(user)) {
            throw new IllegalArgumentException("팁 작성자만 삭제할 수 있습니다.");
        }
        tipRepository.delete(tip);
    }

    // 팁 좋아요
    @Override
    public void likeTip(User user, Long id) {
        Tip tip = tipRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("팁 게시글을 찾을 수 없습니다."));

        if (tip.getUser().equals(user)) {
            throw new IllegalArgumentException("본인의 팁에 좋아요를 누를 수 없습니다.");
        }
        if (tipLikeRepository.existsByUserAndTip(user, tip)) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }
        TipLike tipLike = TipLike.builder()
                .user(user)
                .tip(tip)
                .build();
        tipLikeRepository.save(tipLike);
    }
}



