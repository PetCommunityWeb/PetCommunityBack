package com.example.petback.chat.dto;

import com.example.petback.chat.entity.ChatMessage;
import com.example.petback.chat.entity.ChatRoom;
import com.example.petback.chat.repository.ChatRoomRepository;
import com.example.petback.user.entity.User;
import com.example.petback.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
public class MessageDto {

    public enum MessageType {
        ENTER, TALK // 입장, 메시지전달
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime time = LocalDateTime.now();

    public ChatMessage toEntity(User user, ChatRoom chatRoom) {
        return ChatMessage.builder()
                .message(message)
                .time(time)
                .user(user)
                .chatRoom(chatRoom)
                .build();
    }
}
