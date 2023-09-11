package com.example.petback.user.service;

public interface RefreshTokenService {
    void saveRefreshToken(String refreshToken, Long userId);
}
