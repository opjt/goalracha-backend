package com.goalracha.repository;

import com.goalracha.dto.GroundDTO;
import com.goalracha.dto.reserve.OwnerReserveListDTO;
import com.goalracha.entity.Ground;
import com.goalracha.entity.GroundImage;
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

    // 페이지네이션을 포함한 지정 범위의 Ground 엔티티 조회
    @Query(value = "SELECT * FROM (SELECT g.*, ROWNUM rnum FROM (SELECT * FROM ground ORDER BY g_no DESC) g WHERE ROWNUM <= :endRowNum) WHERE rnum >= :offset", nativeQuery = true)
    List<Ground> findWithPaging(@Param("offset") int offset, @Param("endRowNum") int endRowNum);

    // 상태에 따른 Ground 엔티티 조회
    List<Ground> findByState(Long State);

    // Ground 엔티티의 상태 업데이트
    @Modifying
    @Query("update Ground g set g.state = :state where g.gNo = :gno")
    void updateGroundState(@Param("gno") Long gno, @Param("state") Long state);

    // 지정된 Ground 엔티티와 관련된 이미지를 포함하여 조회
    @EntityGraph(attributePaths = "imageList")
    @Query("select g from Ground g where g.gNo = :gno")
    Optional<Ground> selectOne(@Param("gno") Long gno);

    // Ground 엔티티의 상태를 업데이트하는 메서드
    @Modifying
    @Query("update Ground g set g.state = :state where g.gNo = :gno")
    void groundState(@Param("gno") Long gno, @Param("state") Long state);

    // 소유자의 Ground 목록을 페이지네이션하여 조회
    @Query("SELECT g, gi FROM Ground g LEFT JOIN fetch g.imageList gi WHERE gi.ord = 0 AND g.member.uNo = :uNo")
    Page<Object[]> selectOnwerList(Long uNo, Pageable pageable);

    // 모든 Ground 목록을 페이지네이션하여 조회
    @Query("SELECT g, gi FROM Ground g LEFT JOIN fetch g.imageList gi WHERE gi.ord = 0")
    Page<Object[]> selectList(Pageable pageable);

    // 소유자의 Ground 목록을 이름으로 검색하여 페이지네이션하여 조회
    @Query("SELECT g, gi FROM Ground g LEFT JOIN fetch g.imageList gi WHERE gi.ord = 0 AND g.member.uNo = :uNo AND g.name LIKE CONCAT('%', LOWER(:searchName), '%')")
    Page<Object[]> selectOnwerListSearch(Long uNo, String searchName, Pageable pageable);

    // 지정된 Ground의 이미지 파일 이름 목록 조회
    @Query("SELECT gi.fileDirectory FROM Ground g JOIN g.imageList gi WHERE g.gNo = :gno")
    List<String> findAllImageFileNamesByGNo(@Param("gno") Long gno);
}
