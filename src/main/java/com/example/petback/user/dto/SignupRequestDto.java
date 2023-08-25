package com.example.petback.user.dto;

import com.example.petback.user.entity.User;
import com.example.petback.user.enums.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {
    @NotBlank(message = "null 과 \"\" 과 \" \" 모두 허용하지 않습니다.")
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;

    private String imageUrl;

    @Builder.Default
    private UserRoleEnum role = UserRoleEnum.USER;

    @Email
    @NotBlank
    private String email;

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