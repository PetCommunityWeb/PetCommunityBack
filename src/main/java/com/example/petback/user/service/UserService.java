package com.example.petback.user.service;

import com.example.petback.user.dto.ProfileRequestDto;
import com.example.petback.user.dto.ProfileResponseDto;
import com.example.petback.user.dto.SignupRequestDto;
import com.example.petback.user.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    // 회원가입
    void signUp(SignupRequestDto requestDto);

    // 회원 전체 조회
    List<ProfileResponseDto> selectProfiles();

    // 회원 상세 조회
    ProfileResponseDto selectProfile(Long id);

    // 회원 정보 수정
    void updateProfile(ProfileRequestDto requestDto, User user);
  
    ProfileResponseDto selectMyProfile(User user);

    // 회원 탈퇴
    void deleteProfile(User user, Long id);
    // 회원 탈퇴 복구
    void restoreProfile(Long id);

    User findUser(Long id);

    Map<String, String> refreshToken(String refreshToken);

    Long getUserIdByEmail(String email);
}
