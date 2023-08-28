package com.example.petback.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequestDto {
    private String username;
    private String nickname;
    private String imageUrl;

}