package com.example.petback.common.security;

import com.example.petback.common.jwt.JwtUtil;
import com.example.petback.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final ObjectMapper objectMapper;
    @Autowired // 빈 순환 참조
    private UserService userService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }
    
    // filter에 refreshToken 로직 넣는 이유 => token이 valid하지 않으면 filter 통과 못해 controller로 못넘어가서
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.resolveToken(request);
        String refreshToken = jwtUtil.resolveRefreshToken(request);
        if (token != null) {
            try {
                if (!jwtUtil.validateToken(token)) {
                    throw new IllegalArgumentException("유효하지 않은 JWT 입니다."); // 이것을 ExpiredJwtException 보다 늦게 하는 문제
                }
            } catch (ExpiredJwtException e) { // 다른 jwt Exception이 아닌 만료된 경우에만 재발급하기 위해, trouble: 다른 토큰 값을 임의로 넣어도 되는문제
                Map<String, String> tokens = userService.refreshToken(refreshToken);
                token = tokens.get("accessToken");
                response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token); // header에 담아주기
                token = token.substring(7); // getUserInfo를 위해 substring
            }
            Claims info = jwtUtil.getUserInfoFromToken(token);
            setAuthentication(info.getSubject());
        }
        filterChain.doFilter(request, response);
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
