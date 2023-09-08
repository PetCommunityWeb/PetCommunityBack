package com.example.petback.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomListResponseDto {
    private List<ChatRoomResponseDto> chatRoomResponseDtoList;
}
