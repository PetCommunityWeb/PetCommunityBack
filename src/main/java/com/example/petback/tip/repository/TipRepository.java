package com.example.petback.tip.repository;

import com.example.petback.tip.entity.Tip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipRepository  extends JpaRepository<Tip, Long> {

}
