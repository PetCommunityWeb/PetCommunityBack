package com.example.petback.user.service;

import com.example.petback.user.dto.SignupRequestDto;
import com.example.petback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void signUp(SignupRequestDto requestDto) {
        String password = passwordEncoder.encode(requestDto.getPassword());
        requestDto.setPassword(password);
        userRepository.save(requestDto.toEntity());
    }
}
