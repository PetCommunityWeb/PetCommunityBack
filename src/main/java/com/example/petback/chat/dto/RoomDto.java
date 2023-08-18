package com.example.petback.chat.dto;

import com.example.petback.chat.entity.ChatRoom;
import com.example.petback.chat.service.ChatService;
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

    public void handlerActions(WebSocketSession session, MessageDto messageDto, ChatService chatService) {
        if (messageDto.getType().equals(MessageDto.MessageType.ENTER)) {
            sessions.add(session);
            messageDto.setMessage(messageDto.getSender() + "님이 입장했습니다.");
        }
        sendMessage(messageDto, chatService);

    }

    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }

    public ChatRoom toEntity(String uuid, String roomName) {
        return ChatRoom.builder()
                .uuid(uuid)
                .roomName(roomName)
                .build();
    }
}

