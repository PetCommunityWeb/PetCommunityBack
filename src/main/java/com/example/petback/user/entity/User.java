package com.example.petback.user.entity;

import com.example.petback.chat.entity.ChatMessage;
import com.example.petback.user.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    private String email;
    private String nickname;
    private String imageUrl;

    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    private UserRoleEnum role= UserRoleEnum.USER;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<ChatMessage> chatMessages = new ArrayList<>();
}
