package com.example.petback.chat.controller;

import com.example.petback.chat.dto.ChatRoomResponseDto;
import com.example.petback.chat.dto.RoomDto;
import com.example.petback.chat.dto.RoomRequestDto;
import com.example.petback.chat.service.ChatService;
import com.example.petback.common.security.UserDetailsImpl;
import com.example.petback.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    // 채팅방 생성
    @PostMapping("/chats")
    public RoomDto createRoom(@RequestBody RoomRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatService.createRoom(requestDto.getName(), userDetails.getUser());
    }

    // 채팅방 단일 조회
    @GetMapping("/chat")
    // @Cacheable(value = "chatRoom")
    public ChatRoomResponseDto selectRoom(@RequestParam String uuid) {
        return chatService.selectRoom(uuid);
    }

    // 생성된 채팅방 전체조회
    @GetMapping("/chats")
    public List<ChatRoomResponseDto> selectRooms() {
        return chatService.selectRooms().getChatRoomResponseDtoList();
    }

    // 채팅방 삭제
    @DeleteMapping("/chat")
    public void deleteRoom(@RequestParam String uuid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        chatService.deleteRoom(uuid, userDetails.getUser());
    }
}
