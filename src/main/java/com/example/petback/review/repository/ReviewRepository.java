package com.example.petback.review.repository;

import com.example.petback.review.entity.Review;
import com.example.petback.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByUser(User user);

    @Query(value = "SELECT * FROM reviews WHERE user_id = :userId", nativeQuery = true)
    List<Review> findSoftDeletedReviewsByUserId(Long id);
}
