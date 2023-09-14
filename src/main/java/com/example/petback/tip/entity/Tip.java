package com.example.petback.tip.entity;


import com.example.petback.feed.entity.FeedLike;
import com.example.petback.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
@Table(name = "tip")
@SQLDelete(sql  = "UPDATE tip SET is_deleted = true WHERE id = ?")
public class Tip extends TimeStamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Column(length = 10000)
    private String content;

    @Column
    private String imageUrl;

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn
    private User user;


    @Builder.Default
    @OneToMany(mappedBy = "tip", orphanRemoval = true)
    private List<TipLike> tipLikes = new ArrayList<>();
}
