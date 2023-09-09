package com.example.petback.user.repository;

import com.example.petback.user.dto.UserDto;
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
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users u WHERE u.email = :email", nativeQuery = true)
    User findUserByEmailIgnoringSoftDelete(@Param("email") String email);

}
