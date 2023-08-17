package com.example.petback.common.advice;

import com.example.petback.user.enums.UserRoleEnum;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$",
            message = "최소 4자 이상, 10자 이하의 알파벳 소문자와, 숫자로 구성되어야 합니다.")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+{}:\"<>?,.\\\\/]{8,15}$",
            message = "최소 8자 이상, 15자 이하의 알파벳 대소문자, 숫자, 특수문자로 구성되어야 합니다.")
    private String password;

    private String nickname;

    private UserRoleEnum role;


}

