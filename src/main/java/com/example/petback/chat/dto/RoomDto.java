package com.example.petback.chat.dto;

import com.example.petback.chat.entity.ChatRoom;
import com.example.petback.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class RoomDto {
    private String roomId;
    private String name;
    private String userNickname;
    private String doctorNickname;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public RoomDto(String roomId, String name, String userNickname, String doctorNickname) {
        this.roomId = roomId;
        this.name = name;
        this.userNickname = userNickname;
        this.doctorNickname = doctorNickname;
    }

    public ChatRoom toEntity(String uuid, String roomName, User user, User doctor) {
        return ChatRoom.builder()
                .uuid(uuid)
                .roomName(roomName)
                .user(user)
                .doctor(doctor)
                .build();
    }
}

