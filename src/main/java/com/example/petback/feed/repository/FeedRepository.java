package com.example.petback.feed.repository;

import com.example.petback.feed.entity.Feed;
import com.example.petback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    Optional<Feed> findByTitle(String 테스트피드제목);
    List<Feed> findByUser(User user);
    @Query(value = "SELECT f.* FROM feeds f LEFT JOIN feed_like fl ON f.id = fl.feed_id WHERE f.is_deleted = false GROUP BY f.id ORDER BY COUNT(fl.feed_id) DESC", nativeQuery = true)
    List<Feed> findAllOrderByLikes();

    @Query(value = "SELECT f.* FROM feeds f LEFT JOIN comments c ON f.id = c.feed_id WHERE f.is_deleted = false GROUP BY f.id ORDER BY COUNT(c.feed_id) DESC", nativeQuery = true)
    List<Feed> findAllOrderByCommentCount();
}
