package com.goalracha.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.goalracha.domain.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = "imageList")
    @Query("select p from Product p where p.i_no = :i_no")
    Optional<Product> selectOne(@Param("i_no") Long i_no);

    @Modifying
    @Query("update Product p set p.delFlag = :flag where p.i_no = :i_no")
    void updateToDelete(@Param("i_no") Long pno, @Param("flag") boolean flag);
}
