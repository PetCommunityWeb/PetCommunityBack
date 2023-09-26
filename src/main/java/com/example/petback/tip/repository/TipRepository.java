package com.example.petback.tip.repository;

import com.example.petback.tip.entity.Tip;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TipRepository extends JpaRepository<Tip, Long> {
    List<Tip> findByContentContains(String content);

    // 검색어가 데이터의 50%이상 차지하면, 일반적인 단어로 간주하여 조회결과에서 제외됨 => Boolean Mode 사용
    // @Query(value = "SELECT * FROM tip WHERE MATCH(title) AGAINST(:title)", nativeQuery = true)
    // List<Tip> findByTitleContains(@Param("title") String title);
    
    @Query(value = "SELECT * FROM tip WHERE MATCH(title) AGAINST(:title IN BOOLEAN MODE)", nativeQuery = true)
    List<Tip> findByTitleContains(@Param("title") String title);

    // List<Tip> findByTitleContains(String title);


    Optional<Tip> findById(Long id);

    @Query(value = "SELECT * FROM tip WHERE user_id = :userId" , nativeQuery = true)
    List<Tip> findSoftDeletedTipsByUserId(@Param("userId") Long userId);
}
