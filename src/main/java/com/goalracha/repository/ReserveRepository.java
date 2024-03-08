package com.goalracha.repository;

import com.goalracha.dto.reserve.ReservDTO;
import com.goalracha.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    @Query("SELECT NEW com.goalracha.dto.reserve.ReservDTO(r.rNO,r.payType, r.createDate,  r.reserveDate, r.time, r.state, r.member.uNo, r.ground.gNo) " +
            "FROM Reserve r JOIN r.ground g WHERE g.gNo = :gno AND r.state = 1")
    List<ReservDTO> listGroundReserveDTO(Long gno); //그라운드아이디로 구장 예약목록 불러오기

    @Query("SELECT r.time FROM Reserve r WHERE r.reserveDate = :reserveDate AND r.ground.gNo = :gno")
    List<Integer> findReservationTimesByDate(Long gno, Date reserveDate);

}
