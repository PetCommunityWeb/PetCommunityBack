package com.example.petback.user.entity;

// import com.example.petback.hospital.entity.Hospital;

import com.example.petback.user.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
@EqualsAndHashCode(of = "id")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
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
    private String introduction;

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;

    @Column
    private String imageUrl;

    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

//    @OneToOne(mappedBy = "user")
//    private Hospital hospital;
}
