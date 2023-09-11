package com.example.petback.chat.repository;

import com.example.petback.chat.entity.ChatRoom;
import com.example.petback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    void deleteByUuid(String uuid);
    Optional<ChatRoom> findByUuid(String uuid);
    List<ChatRoom> findAllByUser_IdOrderByCreatedAtDesc(Long userId);
    List<ChatRoom> findAllByDoctor_IdOrderByCreatedAtDesc(Long id);
}
