package com.example.petback.comment.entity;

import com.example.petback.feed.entity.Feed;
import com.example.petback.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "comments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false") // softdelete
@SQLDelete(sql = "UPDATE comments SET is_deleted = true WHERE id = ?")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String content;
    private String username;

    @ManyToOne
    @JoinColumn
    private Feed feed;

    @ManyToOne
    @JoinColumn
    private User user;

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;
}
