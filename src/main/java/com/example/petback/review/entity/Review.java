package com.example.petback.review.entity;

import com.example.petback.reservation.entity.Reservation;
import com.example.petback.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.*;

@Entity
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Where(clause = "is_deleted = false")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private int rate;
    private String imageUrl;

    @OneToOne
    private Reservation reservation;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Review updateTitle(String title) {
        this.title = title;
        return this;
    }

    public Review updateContent(String content) {
        this.content = content;
        return this;
    }

    public Review updateRate(int rate) {
        this.rate = rate;
        return this;
    }

    public Review updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = Boolean.TRUE;
    }

    public void restore() {
        this.isDeleted = Boolean.FALSE;
    }
}
