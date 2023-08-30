package com.example.petback.chat.dto;

import com.example.petback.chat.entity.ChatMessage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatMessageResponseDto {
    private Long messageId;
    private String message;
    private String sender;
    private LocalDateTime time;

    public static ChatMessageResponseDto of (ChatMessage chatMessage) {
        return ChatMessageResponseDto.builder()
                .messageId(chatMessage.getId())
                .sender(chatMessage.getUser().getUsername())
                .message(chatMessage.getMessage())
                .time(chatMessage.getTime())
                .build();
    }
}
