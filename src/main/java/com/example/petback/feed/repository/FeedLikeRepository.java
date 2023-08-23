package com.example.petback.feed.repository;

import com.example.petback.feed.entity.Feed;
import com.example.petback.feed.entity.FeedLike;
import com.example.petback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {
    Boolean existsByUserAndFeed(User user, Feed feed);
    Optional<FeedLike> findByUserAndFeed(User user, Feed feed);
}
