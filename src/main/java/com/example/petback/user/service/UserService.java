package com.example.petback.user.service;

import com.example.petback.user.dto.LoginRequestDto;
import com.example.petback.user.dto.ProfileRequestDto;
import com.example.petback.user.dto.ProfileResponseDto;
import com.example.petback.user.dto.SignupRequestDto;
import com.example.petback.user.entity.User;
import com.example.petback.user.enums.UserRoleEnum;
import com.example.petback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 가입
    public void signUp(SignupRequestDto requestDto) {
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
    }

    // 로그인
    public void logIn(LoginRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    // 회원정보 전체 조회
    @Transactional(readOnly = true)
    public List<ProfileResponseDto> selectProfiles() {
        return userRepository.findAll().stream().map(ProfileResponseDto::new).toList();
    }


    // 회원정보 상세 조회
    @Transactional(readOnly = true)
    public ProfileResponseDto selectProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        return new ProfileResponseDto(user);

    }

    // 회원정보 수정 (닉네임, 프로필 사진)
    public void updateProfile(ProfileRequestDto requestDto, User user) {
        User userProfile = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );
        userProfile.setNickname(requestDto.getNickname());
        userProfile.setImageUrl(requestDto.getImageUrl());

    }

    @Transactional(readOnly = true)
    public ProfileResponseDto selectMyProfile(User user) {
        return new ProfileResponseDto(user);
    }

    // 회원 탈퇴


    //
}
