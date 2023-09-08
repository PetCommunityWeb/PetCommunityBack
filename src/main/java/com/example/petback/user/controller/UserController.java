package com.example.petback.user.controller;

import com.example.petback.common.advice.ApiResponseDto;
import com.example.petback.common.security.UserDetailsImpl;
import com.example.petback.tip.dto.TipResponseDto;
import com.example.petback.user.dto.*;
import com.example.petback.user.entity.User;
import com.example.petback.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signUp(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signUp(requestDto);
        return ResponseEntity.status(201).body(new ApiResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    @GetMapping("/my-profile")
    public ResponseEntity selectMyProfile(@AuthenticationPrincipal UserDetailsImpl userDetails){
        ProfileResponseDto responseDto = userService.selectMyProfile(userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    // 회원정보 전체 조회
    @GetMapping("/profile")
    public ResponseEntity selectProfiles() {
        List<ProfileResponseDto> responseDto = userService.selectProfiles();
        return ResponseEntity.ok().body(responseDto);
    }

    // 회원정보 상세 조회
    @GetMapping("/profile/{id}")
    public ResponseEntity selectProfile(@PathVariable Long id) {
        ProfileResponseDto responseDto = userService.selectProfile(id);
        return ResponseEntity.ok().body(responseDto);
    }

    // 회원정보 수정
    @PutMapping("/profile")
    public ResponseEntity<ApiResponseDto> updateProfile(@RequestBody ProfileRequestDto profileRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
            userService.updateProfile(profileRequestDto, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("프로필 수정 성공", HttpStatus.OK.value()));
    }

    // 회원 탈퇴
    @DeleteMapping("/profile/{id}")
    public ResponseEntity deleteProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        userService.deleteProfile(userDetails.getUser(), id);
        return ResponseEntity.ok().body("삭제가 완료되었습니다.");
    }
    //회원 탈퇴후 복구 -> 입력받은 email을 기준으로 userId 파악
    @PostMapping("/profile/restore")
    public ResponseEntity<String> restoreUserData(@RequestParam String email) {

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("이메일 주소를 입력하세요.");
        }
//        email = email.replace("\"", "");
        Long userId = userService.getUserIdByEmail(email);

        if (userId == null) {
            return ResponseEntity.badRequest().body("해당 이메일로 등록된 사용자가 없습니다.");
        }

        try {
            userService.restoreProfile(userId);
            return ResponseEntity.ok().body("데이터 복구 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
