package com.example.petback.chat.entity;

import com.example.petback.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@Table(name = "chat_rooms")
public class ChatRoom {
    @Id
    private String uuid;

    private String roomName;
    private long createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private User doctor;


    @Builder.Default
    @OneToMany(mappedBy = "chatRoom", orphanRemoval = true)
    private List<ChatMessage> ChatMessages = new ArrayList<>();
}
