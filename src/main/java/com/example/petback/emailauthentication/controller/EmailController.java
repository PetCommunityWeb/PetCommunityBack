package com.example.petback.emailauthentication.controller;

import com.example.petback.common.advice.ApiResponseDto;
import com.example.petback.emailauthentication.dto.EmailVerificationRequestDto;
import com.example.petback.emailauthentication.service.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private final EmailServiceImpl emailService;

    // 인증 코드를 email로 보내기
    @PostMapping("/send-verification")
    public ResponseEntity<ApiResponseDto> sendEmail(@RequestParam("email") String emails) throws Exception {
        emailService.sendEmail(emails);
        return ResponseEntity.ok().body(new ApiResponseDto("이메일 인증 코드 전송 완료", HttpStatus.OK.value()));
    }

    // 받은 인증 코드를 입력하고 검증하기
    @PostMapping("/email-check")
    public ResponseEntity<ApiResponseDto> checkEmail(@RequestBody EmailVerificationRequestDto emailVerificationRequestDto) throws Exception {
        //email  인증코드 확인

        String enterEmail = emailVerificationRequestDto.getEmail();
        String enterVerificationCode = emailVerificationRequestDto.getVerificationCode();

        boolean auth = emailService.verificationCodeCheck(enterEmail, enterVerificationCode);
        if (auth) {
            return ResponseEntity.ok().body(new ApiResponseDto("이메일 인증 성공", HttpStatus.OK.value()));
        }
        return ResponseEntity.badRequest().body(new ApiResponseDto("이메일 인증 실패", HttpStatus.BAD_REQUEST.value()));

    }
}

