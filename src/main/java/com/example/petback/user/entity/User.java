package com.example.petback.user.entity;

import com.example.petback.user.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
    private UserRoleEnum role;

//    @OneToOne(mappedBy = "user")
//    private Hospital hospital;
}
