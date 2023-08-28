package com.example.petback.user.dto;

import com.example.petback.user.entity.User;
import com.example.petback.user.enums.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;

    private String imageUrl;

    @Email
    @NotBlank
    private String email;

    @Builder.Default
    private UserRoleEnum role= UserRoleEnum.USER;

    public void setPassword(String password) {
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .imageUrl(imageUrl)
                .role(role)
                .build();
    }


}