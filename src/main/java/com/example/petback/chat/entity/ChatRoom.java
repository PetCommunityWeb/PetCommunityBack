package com.example.petback.chat.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Table(name = "ChatRooms")
public class ChatRoom {
    @Id
    private String uuid;

    @Column
    private String roomName;

    @Builder.Default
    @OneToMany(mappedBy = "chatRoom", orphanRemoval = true)
    private List<ChatMessage> ChatMessages = new ArrayList<>();
}
