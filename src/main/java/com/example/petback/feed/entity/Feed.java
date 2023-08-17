package com.example.petback.feed.entity;


import com.example.petback.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn
    private User user;

//    @OneToMany
//    @JoinColumn
//    private List<Comment> comments;
}
