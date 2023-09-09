package com.example.petback.user.dto;

import com.example.petback.user.entity.User;
import com.example.petback.user.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDto {
    private Long id;
    private String username;
    private String nickname;
    private String imageUrl;
    private String email;
    private UserRoleEnum role;
    private String introduction;

    public ProfileResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.imageUrl = user.getImageUrl();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.introduction=user.getIntroduction();
    }
}