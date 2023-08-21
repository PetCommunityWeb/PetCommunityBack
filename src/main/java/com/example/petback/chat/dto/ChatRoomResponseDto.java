package com.example.petback.chat.dto;

import com.example.petback.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChatRoomResponseDto {
    private String uuid;
    private String roomName;
    private List<ChatMessageResponseDto> chatMessages;

    public static ChatRoomResponseDto of(ChatRoom chatRoom) {
        return ChatRoomResponseDto.builder()
                .uuid(chatRoom.getUuid())
                .roomName(chatRoom.getRoomName())
                .chatMessages(chatRoom.getChatMessages().stream().map(ChatMessageResponseDto::of).toList())
                .build();
    }
}
