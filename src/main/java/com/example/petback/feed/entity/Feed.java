package com.example.petback.feed.entity;


import com.example.petback.comment.entity.Comment;
import com.example.petback.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "feeds")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "is_deleted = false")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String imageUrl;

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "feed", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "feed", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<FeedLike> feedLikes = new ArrayList<>();

    public void update(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public void setDeleted() {
        this.isDeleted = Boolean.TRUE;
    }
    public void restore() {
        this.isDeleted=Boolean.FALSE;
    }
}