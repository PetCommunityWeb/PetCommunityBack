package com.example.petback.chat.service;

import com.example.petback.chat.dto.ChatRoomResponseDto;
import com.example.petback.chat.dto.RoomDto;
import com.example.petback.chat.entity.ChatRoom;
import com.example.petback.chat.repository.ChatRoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private Map<String, RoomDto> chatRooms;

    @PostConstruct // 빈 생성과 의존성 주입이 완료된 후 호출되는 초기화 메소드
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<RoomDto> findRooms() {
        return new ArrayList<>(chatRooms.values());
    }

    public RoomDto selectRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    @Transactional
    public RoomDto createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        RoomDto roomDto = RoomDto.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRooms.put(randomId, roomDto);

        ChatRoom chatRoom = roomDto.toEntity(randomId, name);
        chatRoomRepository.save(chatRoom);
        return roomDto;
    }

    @Transactional(readOnly = true)
    public ChatRoomResponseDto selectRoom(String uuid) {
        ChatRoom chatRoom = chatRoomRepository.findByUuid(uuid).get();
        return ChatRoomResponseDto.of(chatRoom);
    }

    @Transactional(readOnly = true)
    public List<ChatRoomResponseDto> selectRooms() {
        return chatRoomRepository.findAll().stream().map(ChatRoomResponseDto::of).toList();
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            // throw new RuntimeException(e);
        }
    }

}
