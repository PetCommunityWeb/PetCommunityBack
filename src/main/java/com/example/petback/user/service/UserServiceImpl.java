package com.example.petback.user.service;

import com.example.petback.common.jwt.JwtUtil;
import com.example.petback.common.jwt.RefreshToken;
import com.example.petback.user.dto.ProfileRequestDto;
import com.example.petback.user.dto.ProfileResponseDto;
import com.example.petback.user.dto.SignupRequestDto;
import com.example.petback.user.entity.User;
import com.example.petback.user.enums.UserRoleEnum;
import com.example.petback.user.repository.RefreshTokenRepository;
import com.example.petback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    // 회원 가입
    @Override
    public Long signUp(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        requestDto.setPassword(password);
        String email = requestDto.getEmail();
        UserRoleEnum role = requestDto.getRole();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        User user = new User();
        userRepository.save(requestDto.toEntity());
        return user.getId();
    }


    // 회원정보 전체 조회

    @Override
    public List<ProfileResponseDto> selectProfiles() {
        return userRepository.findAll().stream().map(ProfileResponseDto::new).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileResponseDto selectMyProfile(User user) {
        return new ProfileResponseDto(user);
    }

    // 회원정보 상세 조회
    @Override
    public ProfileResponseDto selectProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        return new ProfileResponseDto(user);

    }

    // 회원정보 수정 (닉네임, 프로필 사진)
    @Override
    public void updateProfile(ProfileRequestDto requestDto, User user) {
        User userProfile = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );
        userProfile.setNickname(requestDto.getNickname());
        userProfile.setImageUrl(requestDto.getImageUrl());

    }

    // 회원 탈퇴
    @Override
    public void deleteProfile(User user, Long id) {
        User user1 = findUser(id);
        if (!user.equals(user1.getId())) throw new IllegalArgumentException("탈퇴 권한이 없습니다.");
        userRepository.delete(user1);
    }

    @Override
    public User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    @Override
    public Map<String, String> refreshToken(String refreshToken) {
        RefreshToken redisToken = refreshTokenRepository.findById(refreshToken).get(); // 무조건 @Id로만 찾기
        Long userId = redisToken.getUserId();
        User user = userRepository.findById(userId).get();
        Map<String, String> tokens = new HashMap<>();
        String newToken = jwtUtil.createToken(user.getUsername(), user.getRole(), user.getId());
        tokens.put("accessToken", newToken);
        tokens.put("refreshToken", refreshToken); // 기존 refreshToken 유효하므로 그대로 반환
        return tokens;
    }
}
