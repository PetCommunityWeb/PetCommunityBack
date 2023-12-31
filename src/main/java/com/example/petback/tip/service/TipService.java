package com.example.petback.tip.service;

import com.example.petback.tip.dto.TipListResponseDto;
import com.example.petback.tip.dto.TipRequestDto;
import com.example.petback.tip.dto.TipResponseDto;
import com.example.petback.user.entity.User;

public interface TipService {

    // 팁 작성
    TipResponseDto createTip(TipRequestDto requestDto, User user);

    // 팁 전체 조회
    TipListResponseDto selectTips();

    // 팁 상세 조회
    TipResponseDto selectTip(Long id);

    // 팁 제목 조회
    TipListResponseDto searchTitle(String keyword);

    // 팁 내용 조회
    TipListResponseDto searchContent(String keyword);

    // 팁 수정
    TipResponseDto updateTip(Long id, TipRequestDto requestDto, User user);

    // 팁 삭제
    void deleteTip(Long id, User user);

    // 팁 좋아요
    void likeTip(User user, Long id);
}
