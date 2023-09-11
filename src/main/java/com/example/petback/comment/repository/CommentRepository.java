package com.example.petback.comment.repository;

import com.example.petback.comment.entity.Comment;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM comments WHERE user_id = :userId", nativeQuery = true)
    List<Comment> findSoftDeletedCommentsByUserId(@Param("userId") Long userId);

}
