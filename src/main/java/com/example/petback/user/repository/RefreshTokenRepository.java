package com.example.petback.user.repository;

import com.example.petback.common.jwt.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    RefreshToken findByUserId(Long userId);
    RefreshToken findByRefreshToken(String refreshToken);

}
