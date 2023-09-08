package com.example.petback.user.entity;

// import com.example.petback.hospital.entity.Hospital;

import com.example.petback.chat.entity.ChatMessage;
import com.example.petback.chat.entity.ChatRoom;
import com.example.petback.comment.entity.Comment;
import com.example.petback.feed.entity.Feed;
import com.example.petback.feed.entity.FeedLike;
import com.example.petback.hospital.entity.Hospital;
import com.example.petback.reservation.entity.Reservation;
import com.example.petback.review.entity.Review;
import com.example.petback.user.dto.ProfileRequestDto;
import com.example.petback.user.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name="email", nullable = false, unique = true)
    private String email;

    private String nickname;
    private String introduction;

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;

    @Column
    private String imageUrl;

    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    // USER 삭제 시, 같이 삭제되야할 것들
    // 피드, 피드 좋아요, 피드 댓글
    // 팁, 팁 좋아요, 팁 댓글
    // 등록 병원
    // 리뷰 , 예약, 실시간 채팅,
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Feed> feeds = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<FeedLike> feedLikes = new ArrayList<>();

    //    @OneToMany(cascade = CascadeType.REMOVE)
//    @Builder.Default
//    private List<Tip> tips = new ArrayList<>();
//
//    @OneToMany(cascade = CascadeType.REMOVE)
//    @Builder.Default
//    private List<TipLike> tipLikes = new ArrayList<>();
//
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Hospital> hospitals = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<ChatRoom> chatRooms = new ArrayList<>();

    public void updateProfile(ProfileRequestDto requestDto) {
        this.nickname = requestDto.getNickname();
        this.imageUrl = requestDto.getImageUrl();
        this.introduction = requestDto.getIntroduction();
    }
}
