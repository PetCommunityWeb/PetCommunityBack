package com.example.petback.user.dto;

import com.example.petback.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDto {
    private String username;
    private String nickname;
    private String imageUrl;

    public ProfileResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.imageUrl = user.getImageUrl();
    }
}