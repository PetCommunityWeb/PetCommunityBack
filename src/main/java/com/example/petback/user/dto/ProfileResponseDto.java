package com.example.petback.user.dto;

import com.example.petback.user.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String nickname;
    private String imageUrl;

    public ProfileResponseDto(User user) {
        this.nickname = user.getNickname();
        this.imageUrl = user.getImageUrl();
    }
}