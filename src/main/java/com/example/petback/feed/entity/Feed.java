package com.example.petback.feed.entity;


import com.example.petback.comment.entity.Comment;
import com.example.petback.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "feeds")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false") // !!!!
@SQLDelete(sql = "UPDATE feed SET is_deleted = true WHERE id = ?")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "feed", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "feed", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<FeedLike> feedLikes = new ArrayList<>();  //좋아요 수?


    //    @OneToMany
//    @JoinColumn
//    private List<Comment> comments;
}
