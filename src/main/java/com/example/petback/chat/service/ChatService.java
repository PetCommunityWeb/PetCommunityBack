package com.example.petback.chat.service;

import com.example.petback.chat.dto.ChatRoomResponseDto;
import com.example.petback.chat.dto.RoomDto;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

public interface ChatService {

    RoomDto selectRoomById(String roomId);

    RoomDto createRoom(String name);

    ChatRoomResponseDto selectRoom(String uuid);

    List<ChatRoomResponseDto> selectRooms();

    <T> void sendMessage(WebSocketSession session, T message);
}
