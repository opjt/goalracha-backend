package com.goalracha.repository;

import com.goalracha.domain.Ground;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroundRepository extends JpaRepository<Ground, Long> {
    
}
