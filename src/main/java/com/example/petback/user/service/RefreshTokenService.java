package com.example.petback.user.service;

import com.example.petback.common.jwt.RefreshToken;

public interface RefreshTokenService {
    void saveRefreshToken(String refreshToken, Long userId);

    void deleteRefreshToken(String token);
}
