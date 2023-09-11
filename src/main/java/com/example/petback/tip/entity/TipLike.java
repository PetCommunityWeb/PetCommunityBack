package com.example.petback.tip.entity;

import com.example.petback.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tip_like")
public class TipLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;

    public void setDeleted() {
        this.isDeleted = Boolean.TRUE;
    }

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 지연 로딩
    @JoinColumn(name = "tip_id")
    private Tip tip;

    public TipLike(User user, Tip tip) {
        this.user = user;
        this.tip = tip;
    }

    public void restore() {
        this.isDeleted = Boolean.FALSE;
    }
}
