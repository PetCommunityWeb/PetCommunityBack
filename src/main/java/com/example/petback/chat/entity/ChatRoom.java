package com.example.petback.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "ChatRooms")
public class ChatRoom {
    @Id
    private String uuid;

    @Column
    private String roomName;

    @OneToMany(mappedBy = "chatRoom", orphanRemoval = true)
    private List<ChatMessage> ChatMessages = new ArrayList<>();
}
