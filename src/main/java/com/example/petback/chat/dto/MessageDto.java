package com.example.petback.chat.dto;

import com.example.petback.chat.entity.ChatMessage;
import com.example.petback.chat.entity.ChatRoom;
import com.example.petback.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

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
    private ZonedDateTime time = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

    public ChatMessage toEntity(User user, ChatRoom chatRoom) {
        return ChatMessage.builder()
                .message(message)
                .time(time.toLocalDateTime())
                .user(user)
                .chatRoom(chatRoom)
                .build();
    }
}
