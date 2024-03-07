package com.goalracha.repository;

import com.goalracha.entity.Ground;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroundRepository extends JpaRepository<Ground, Long> {
    @Query(value = "SELECT * FROM (SELECT g.*, ROWNUM rnum FROM (SELECT * FROM ground ORDER BY g_no DESC) g WHERE ROWNUM <= :endRowNum) WHERE rnum >= :offset", nativeQuery = true)
    List<Ground> findWithPaging(@Param("offset") int offset, @Param("endRowNum") int endRowNum);
}
