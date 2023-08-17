package com.example.petback.user.service;

import com.example.petback.common.advice.AuthRequestDto;
import com.example.petback.user.entity.User;
import com.example.petback.user.enums.UserRoleEnum;
import com.example.petback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 1. 회원가입
    public void signup(AuthRequestDto authRequestDto) {
        String username = authRequestDto.getUsername();
        String password = passwordEncoder.encode(authRequestDto.getPassword());
        String nickname = authRequestDto.getNickname();
        UserRoleEnum role = authRequestDto.getRole();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // User DB에 회원 정보 저장
        User user = new User (username, password, nickname, role);
        userRepository.save(user);
    }

    // 2. 로그인
    public void login(AuthRequestDto authRequestDto) {

        String username = authRequestDto.getUsername();
        String password = authRequestDto.getPassword();

        // DB에 사용자가 입력한 정보가 있는지 확인 -> 없으면 에러메시지 날리기
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        // 비밀번호 일치하는지 확인하기
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

}

