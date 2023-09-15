package com.example.petback.tip.repository;

import com.example.petback.tip.entity.Tip;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TipRepository  extends JpaRepository<Tip, Long> {
    List<Tip> findByContentContains(String content);
    List<Tip> findByTitleContains(String title);

    Optional<Tip> findById(Long id);


    @Query(value = "SELECT * FROM tip WHERE user_id = :userId" , nativeQuery = true)
    List<Tip> findSoftDeletedTipsByUserId(@Param("userId") Long userId);
}
