package com.example.petback.user.entity;

import com.example.petback.hospital.entity.Hospital;

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
@Table(name = "users")
@EqualsAndHashCode(of = "id")
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

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Hospital> hospital = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<ChatMessage> chatMessages = new ArrayList<>();
}
