package com.example.petback.user.dto;

import com.example.petback.user.entity.User;
import com.example.petback.user.enums.UserRoleEnum;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private Long id;
    private String nickname;
    private String imageUrl;
    private String email;
    private UserRoleEnum role;

    public ProfileResponseDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.imageUrl = user.getImageUrl();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}