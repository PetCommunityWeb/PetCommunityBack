package com.example.petback.chat.entity;

import com.example.petback.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "ChatMessages")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE chat_messages SET is_deleted = true WHERE id = ?")

public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String message;

    @Column
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ChatRoom chatRoom;

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = Boolean.TRUE;
    }
}
