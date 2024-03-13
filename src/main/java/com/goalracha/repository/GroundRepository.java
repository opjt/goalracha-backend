package com.goalracha.repository;

import com.goalracha.entity.Ground;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroundRepository extends JpaRepository<Ground, Long> {

    @EntityGraph(attributePaths = "imageList")
    @Query("select g from Ground g where g.gNo = :gno")
    Optional<Ground> selectOne(@Param("gno") Long gno);

    @Modifying
    @Query("update Ground g set g.state = :state where g.gNo = :gno")
    void groundState(@Param("gno") Long gno, @Param("state") Long state);

    @Query("SELECT g, gi FROM Ground g LEFT JOIN fetch g.imageList gi WHERE gi.ord = 0")
    Page<Object[]> selectList(Pageable pageable);


}
