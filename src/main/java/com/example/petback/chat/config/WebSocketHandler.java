package com.example.petback.chat.config;

import com.example.petback.chat.dto.MessageDto;
import com.example.petback.chat.entity.ChatMessage;
import com.example.petback.chat.entity.ChatRoom;
import com.example.petback.chat.repository.ChatMessageRepository;
import com.example.petback.chat.repository.ChatRoomRepository;
import com.example.petback.user.entity.User;
import com.example.petback.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j(topic = "WebSocketHandler")
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final StringRedisTemplate redisTemplate;

    // 각 채팅방 별로 WebSocket 세션을 저장하는 맵
    private Map<String, Set<WebSocketSession>> roomSessionsMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // URI에서 uuid 파라미터 값을 추출
        String roomId = extractRoomIdFromSession(session);

        // 현재 세션을 해당 채팅방의 세션 목록에 추가
        roomSessionsMap
                .computeIfAbsent(roomId, id -> ConcurrentHashMap.newKeySet())
                .add(session);
    }
    private String extractRoomIdFromSession(WebSocketSession session) {
        // URI에서 uuid 파라미터 값을 추출하는 로직을 작성
        // 예: ws://localhost:8080/ws/chat?uuid=sample-uuid 여기에서 'sample-uuid'를 추출
        URI sessionUri = session.getUri();
        if (sessionUri == null) return null;

        String query = sessionUri.getQuery();
        if (query == null || !query.startsWith("uuid=")) return null;

        return query.split("uuid=")[1];
    }
    @Override
    @Transactional
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        MessageDto messageDto = objectMapper.readValue(payload, MessageDto.class);
        String roomId = messageDto.getRoomId();

        User user = userRepository.findByUsername(messageDto.getSender()).get();
        ChatRoom chatRoom = chatRoomRepository.findByUuid(roomId).get();
        ChatMessage chatMessage = messageDto.toEntity(user, chatRoom);
        chatMessageRepository.save(chatMessage);

        for (WebSocketSession roomSession : roomSessionsMap.get(roomId)) {
            roomSession.sendMessage(message);
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // 연결이 종료되면 모든 채팅방에서 해당 세션을 제거
        roomSessionsMap.values().forEach(sessions -> sessions.remove(session));
    }
}

