package com.example.petback.user.repository;

import com.example.petback.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);


    void deleteByUsername(String testUser);

    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isDeleted = true")
    User findByEmailIgnoringSoftDelete(@Param("email") String email);

    @Modifying
    @Query("UPDATE User u SET u.isDeleted = false WHERE u.email = :email")
    void restoreByEmail(@Param("email") String email);
}
