package com.example.petback.chat.service;

import com.example.petback.chat.dto.ChatRoomListResponseDto;
import com.example.petback.chat.dto.ChatRoomResponseDto;
import com.example.petback.chat.dto.RoomDto;
import com.example.petback.chat.dto.RoomRequestDto;
import com.example.petback.user.entity.User;
import org.springframework.web.socket.WebSocketSession;

public interface ChatService {

    RoomDto selectRoomById(String roomId);

    RoomDto createRoom(RoomRequestDto requestDto, User user);

    ChatRoomResponseDto selectRoom(String uuid);

    ChatRoomListResponseDto selectRooms(User user);

    <T> void sendMessage(WebSocketSession session, T message);

    void deleteRoom(String uuid, User user);
}
