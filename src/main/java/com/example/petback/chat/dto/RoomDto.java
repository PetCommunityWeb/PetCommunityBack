package com.example.petback.chat.dto;

import com.example.petback.chat.entity.ChatRoom;
import com.example.petback.chat.service.ChatService;
import com.example.petback.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class RoomDto {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public RoomDto(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public ChatRoom toEntity(String uuid, String roomName, User user) {
        return ChatRoom.builder()
                .uuid(uuid)
                .roomName(roomName)
                .user(user)
                .build();
    }
}

