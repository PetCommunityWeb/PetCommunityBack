package com.example.petback.chat.controller;

import com.example.petback.chat.dto.ChatRoomResponseDto;
import com.example.petback.chat.dto.RoomDto;
import com.example.petback.chat.dto.RoomRequestDto;
import com.example.petback.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    // 채팅방 생성
    @PostMapping("/chats")
    public RoomDto createRoom(@RequestBody RoomRequestDto requestDto) {
        return chatService.createRoom(requestDto.getName());
    }

    // 채팅방 단일 조회
    @GetMapping("/chat")
    public ChatRoomResponseDto selectRoom(@RequestParam String uuid) {
        return chatService.selectRoom(uuid);
    }

    // 생성된 채팅방 전체조회
    @GetMapping("/chats")
    public List<ChatRoomResponseDto> selectRooms() {
        return chatService.selectRooms();
    }
}
