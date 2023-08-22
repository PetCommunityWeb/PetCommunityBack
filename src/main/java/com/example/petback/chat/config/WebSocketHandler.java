package com.example.petback.chat.config;

import com.example.petback.chat.dto.MessageDto;
import com.example.petback.chat.dto.RoomDto;
import com.example.petback.chat.entity.ChatRoom;
import com.example.petback.chat.repository.ChatMessageRepository;
import com.example.petback.chat.repository.ChatRoomRepository;
import com.example.petback.chat.service.ChatService;
import com.example.petback.user.entity.User;
import com.example.petback.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j(topic = "WebSocketHandler")
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    // request 보내면 실행
    @Override
    @Transactional
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        // log.info("{}", payload);

        MessageDto messageDto = objectMapper.readValue(payload, MessageDto.class);

        User user = userRepository.findByUsername(messageDto.getSender()).get();
        ChatRoom chatRoom = chatRoomRepository.findByUuid(messageDto.getRoomId()).get();
        chatMessageRepository.save(messageDto.toEntity(user, chatRoom));

        RoomDto roomDto = chatService.selectRoomById(messageDto.getRoomId());
        roomDto.handlerActions(session, messageDto, chatService);
    }
}
