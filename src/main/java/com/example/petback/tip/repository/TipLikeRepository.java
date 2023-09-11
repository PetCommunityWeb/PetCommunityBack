package com.example.petback.tip.repository;

import com.example.petback.tip.entity.Tip;
import com.example.petback.tip.entity.TipLike;
import com.example.petback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TipLikeRepository extends JpaRepository<TipLike, Long> {

    TipLike findByUserAndTip(User user, Tip tip);

    @Query(value = "SELECT * FROM tip_like WHERE user_id = :userId", nativeQuery = true)
    List<TipLike> findSoftDeletedTipLikesByUserId(Long id);
}
