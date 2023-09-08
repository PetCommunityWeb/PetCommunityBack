package com.example.petback.chat.dto;

import com.example.petback.chat.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
