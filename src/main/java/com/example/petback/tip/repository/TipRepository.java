package com.example.petback.tip.repository;

import com.example.petback.tip.entity.Tip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipRepository  extends JpaRepository<Tip, Long> {

    Optional<Tip> findById(Long id);


}
