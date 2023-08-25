package com.example.petback.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequestDto {
    private String nickname;
    private String imageUrl;
}