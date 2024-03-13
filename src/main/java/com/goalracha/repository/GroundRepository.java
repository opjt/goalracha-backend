package com.goalracha.repository;

import com.goalracha.dto.GroundDTO;
import com.goalracha.entity.Ground;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroundRepository extends JpaRepository<Ground, Long> {
    @Query(value = "SELECT * FROM (SELECT g.*, ROWNUM rnum FROM (SELECT * FROM ground ORDER BY g_no DESC) g WHERE ROWNUM <= :endRowNum) WHERE rnum >= :offset", nativeQuery = true)
    List<Ground> findWithPaging(@Param("offset") int offset, @Param("endRowNum") int endRowNum);

    @Query("SELECT new com.goalracha.dto.GroundDTO(g.gNo, g.name, g.addr, g.inAndOut, g.width, " +
            "g.grassInfo, g.recommdMan, g.usageTime, g.openTime, g.closeTime, g.fare, " +
            "g.userGuide, g.userRules, g.refundRules, g.vestIsYn, " +
            "g.footwearIsYn, g.showerIsYn, g.ballIsYn, g.airconIsYn, g.parkareaIsYn, " +
            "g.roopIsYn, g.state, g.member.uNo) FROM Ground g WHERE g.state != 0")
    List<GroundDTO> findAllGroundsWithoutMember();

    @Query("SELECT new com.goalracha.dto.GroundDTO(g.gNo, g.name, g.addr, g.inAndOut, g.width, " +
            "g.grassInfo, g.recommdMan, g.usageTime, g.openTime, g.closeTime, g.fare, " +
            "g.userGuide, g.userRules, g.refundRules, g.vestIsYn, " +
            "g.footwearIsYn, g.showerIsYn, g.ballIsYn, g.airconIsYn, g.parkareaIsYn, g.roopIsYn, g.state, g.member.uNo) FROM Ground g  WHERE g.gNo IN :gnos")
    List<GroundDTO> findGroundsByGNoIn(@Param("gnos") List<Long> gnos);

    @Modifying
    @Query("update Ground g set g.state = :state where g.gNo = :gno")
    void updateGroundState(@Param("gno") Long gno, @Param("state") Long state);

}
