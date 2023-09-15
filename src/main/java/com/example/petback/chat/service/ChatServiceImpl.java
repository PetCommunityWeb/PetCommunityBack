package com.example.petback.chat.service;

import com.example.petback.chat.dto.ChatRoomListResponseDto;
import com.example.petback.chat.dto.ChatRoomResponseDto;
import com.example.petback.chat.dto.RoomDto;
import com.example.petback.chat.dto.RoomRequestDto;
import com.example.petback.chat.entity.ChatRoom;
import com.example.petback.chat.repository.ChatRoomRepository;
import com.example.petback.user.entity.User;
import com.example.petback.user.enums.UserRoleEnum;
import com.example.petback.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private Map<String, RoomDto> chatRooms;

    @PostConstruct // 빈 생성과 의존성 주입이 완료된 후 호출되는 초기화 메소드
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    @Override
    @Transactional
    public RoomDto selectRoomById(String roomId) {
        ChatRoom room = chatRoomRepository.findByUuid(roomId)
                .orElseThrow(() -> new IllegalArgumentException("해당 채팅방이 존재하지 않습니다."));
        return RoomDto.builder()
                .roomId(room.getUuid())
                .name(room.getRoomName())
                .userNickname(room.getUser().getNickname())
                .doctorNickname(room.getDoctor().getNickname())
                .build();
    }

    @Override
    @Transactional
    @CacheEvict(value = "chatRooms", key = "#user.id")
    public RoomDto createRoom(RoomRequestDto requestDto, User user) {
        String randomId = UUID.randomUUID().toString();
        User doctor = userRepository.findById(requestDto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("해당 의사가 없습니다."));
        RoomDto roomDto = RoomDto.builder()
                .roomId(randomId)
                .name(requestDto.getName())
                .userNickname(user.getNickname())
                .doctorNickname(doctor.getNickname())
                .build();
        chatRooms.put(randomId, roomDto);

        ChatRoom chatRoom = roomDto.toEntity(randomId, requestDto.getName(), user, doctor);
        chatRoomRepository.save(chatRoom);
        return roomDto;
    }

    @Override
    @Transactional(readOnly = true)
    public ChatRoomResponseDto selectRoom(String uuid) {
        ChatRoom chatRoom = chatRoomRepository.findByUuid(uuid).get();
        return ChatRoomResponseDto.of(chatRoom);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "chatRooms", key = "#user.id")
    public ChatRoomListResponseDto selectRooms(User user) {
        if (user.getRole().equals(UserRoleEnum.USER))
            return ChatRoomListResponseDto.builder()
                    .chatRoomResponseDtoList(chatRoomRepository.findAllByUser_IdOrderByCreatedAtDesc(user.getId()).stream().map(ChatRoomResponseDto::of).toList())
                    .build();
        return ChatRoomListResponseDto.builder()
                .chatRoomResponseDtoList(chatRoomRepository.findAllByDoctor_IdOrderByCreatedAtDesc(user.getId()).stream().map(ChatRoomResponseDto::of).toList())
                .build();

    }

    @Override
    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "chatRooms", key = "#user.id")
    public void deleteRoom(String uuid, User user) {
        ChatRoom chatRoom = chatRoomRepository.findByUuid(uuid).get();
        if (!user.equals(chatRoom.getUser())) {
            throw new IllegalArgumentException("채팅방 관리자가 아닙니다.");
        }
        chatRoomRepository.deleteByUuid(uuid);
    }
}
