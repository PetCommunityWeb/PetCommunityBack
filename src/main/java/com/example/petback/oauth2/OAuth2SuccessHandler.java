package com.example.petback.oauth2;


import com.example.petback.common.jwt.JwtUtil;
import com.example.petback.common.jwt.RefreshToken;
import com.example.petback.user.entity.User;
import com.example.petback.user.enums.UserRoleEnum;
import com.example.petback.user.repository.RefreshTokenRepository;
import com.example.petback.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${front.server.url}")
    private String frontUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, Object> attributes = ((OAuth2User) authentication.getPrincipal()).getAttributes();
        String username = (String) attributes.get("name");
        if (attributes.containsKey("kakao_account")) {
            username = (String) ((Map<String, Object>) ((Map<String, Object>)
                    attributes.get("kakao_account"))
                    .get("profile"))
                    .get("nickname");
        }
        UserRoleEnum role = UserRoleEnum.USER;
        attributes.forEach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저 정보가 존재하지 않습니다."));
        String refreshTokenVal = UUID.randomUUID().toString();
        refreshTokenRepository.save(new RefreshToken(refreshTokenVal, user.getId()));
        String token = jwtUtil.createToken(username, role, user.getId());
        token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");
        // 쿠키 생성
        Cookie refreshToken = new Cookie("refreshToken", refreshTokenVal);
        Cookie accessToken = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token);
        refreshToken.setPath("/");
        accessToken.setPath("/"); // 쿠키의 유효 범위 설정 (루트 경로에 모든 요청에 대해 쿠키 전송)
        // 쿠키를 응답 헤더에 추가
        response.addCookie(refreshToken);
        response.addCookie(accessToken);
        response.sendRedirect(frontUrl + "/oauth2");

        log.info("로그인성공");
    }
}