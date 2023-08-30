package com.example.petback.feed.repository;

import com.example.petback.feed.entity.Feed;
import com.example.petback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    Optional<Feed> findByTitle(String 테스트피드제목);
    List<Feed> findByUser(User user);
}
