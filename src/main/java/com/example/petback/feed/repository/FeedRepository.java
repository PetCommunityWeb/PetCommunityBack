package com.example.petback.feed.repository;

import com.example.petback.feed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    Optional<Feed> findByTitle(String 테스트피드제목);
}
