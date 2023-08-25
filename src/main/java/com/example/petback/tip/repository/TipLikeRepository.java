package com.example.petback.tip.repository;

import com.example.petback.tip.entity.Tip;
import com.example.petback.tip.entity.TipLike;
import com.example.petback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipLikeRepository extends JpaRepository<TipLike, Long> {

    TipLike findByUserAndTip(User user, Tip tip);
}
