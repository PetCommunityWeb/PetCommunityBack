package com.example.petback.tip.entity;


import com.example.petback.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "is_deleted = false")
@Table(name = "tip")
@SQLDelete(sql  = "UPDATE tip SET is_deleted = true WHERE id = ?")
public class Tip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @Column
    private String imageUrl;

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn
    private User user;
}
