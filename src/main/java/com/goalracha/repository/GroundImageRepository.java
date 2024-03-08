package com.goalracha.repository;

import com.goalracha.entity.GroundImage;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroundImageRepository extends JpaRepository<GroundImage, Long> {
    @EntityGraph(attributePaths = "imageList")
    @Query("select g from GroundImage g where g.iNo = :i_no")
    Optional<GroundImage> selectOne(@Param("i_no") Long i_no);

    @Modifying
    @Query("update GroundImage g set g.delFlag = :flag where g.iNo = :i_no")
    void updateToDelete(@Param("i_no") Long pno, @Param("flag") boolean flag);
}
