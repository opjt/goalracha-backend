package com.goalracha.repository;

import com.goalracha.dto.reserve.AdminReserveListDTO;
import com.goalracha.dto.reserve.OwnerReserveListDTO;
import com.goalracha.dto.reserve.ReservDTO;
import com.goalracha.dto.reserve.UserReserveListDTO;
import com.goalracha.entity.Member;
import com.goalracha.entity.Reserve;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    @Query("SELECT NEW com.goalracha.dto.reserve.ReservDTO(r.rNO,r.payType, r.createDate,  r.reserveDate, r.time, r.state,r.price, r.member.uNo, r.ground.gNo) " +
            "FROM Reserve r JOIN r.ground g WHERE g.gNo = :gno AND r.state = 1 and function('to_char', r.reserveDate, 'yyyy-mm-dd') = :date ")
    List<ReservDTO> listGroundReserveDTO(Long gno, String date); //그라운드아이디로 구장 예약목록 불러오기

    @Query("SELECT r.time FROM Reserve r WHERE r.reserveDate = :reserveDate AND r.ground.gNo = :gno")
    List<Integer> findReservationTimesByDate(Long gno, Date reserveDate);

    @Query("SELECT g.gNo, LISTAGG(to_char(r.time), ', ') WITHIN GROUP (ORDER BY r.rNO) AS gg " +
            "FROM Ground g " +
            "LEFT OUTER JOIN Reserve r ON g.gNo = r.ground.gNo AND FUNCTION('to_char', r.reserveDate, 'yyyy-mm-dd') = :date " +
            "where g.state != 0" +
            "GROUP BY g.gNo")
    List<Object[]> findGroundsWithReservationsOnDate(@Param("date") String date);


    // user 이전 예약 리스트
    @Query("SELECT new com.goalracha.dto.reserve.UserReserveListDTO(g.name, g.addr, r.reserveDate, r.time, r.createDate, r.price) " +
            "FROM Reserve r " +
            "JOIN r.ground g " +
            "WHERE r.member.uNo = :uNo AND TO_DATE(TO_CHAR(r.reserveDate, 'YYYY-MM-DD'), 'YYYY-MM-DD') < TO_DATE(TO_CHAR(CURRENT_DATE, 'YYYY-MM-DD'), 'YYYY-MM-DD')")
    Page<UserReserveListDTO> userPreviousReservations(@Param("uNo") Long uNo, Pageable pageable);


    // user 예약현황 리스트
    @Query("SELECT new com.goalracha.dto.reserve.UserReserveListDTO(g.name, g.addr, r.reserveDate, r.time, r.createDate, r.price) " +
            "FROM Reserve r " +
            "JOIN r.ground g " +
            "WHERE r.member.uNo = :uNo AND TO_DATE(TO_CHAR(r.reserveDate, 'YYYY-MM-DD'), 'YYYY-MM-DD') >= TO_DATE(TO_CHAR(CURRENT_DATE, 'YYYY-MM-DD'), 'YYYY-MM-DD')")
    Page<UserReserveListDTO> userReservationStatus(@Param("uNo") Long uNo, Pageable pageable);

    @Query("SELECT new com.goalracha.dto.reserve.OwnerReserveListDTO(g.name, g.addr , r.reserveDate, r.time, r.createDate, r.price, m.name, m.email) " +
            "FROM Reserve r " +
            "JOIN r.member m " +
            "JOIN r.ground g " +
            "WHERE g.member.uNo = :uNo")
    Page<OwnerReserveListDTO> ownerReserveList(@Param("uNo") Long uNo, Pageable pageable);

    //Admin 예약 전체 리스트
    @Query("SELECT new com.goalracha.dto.reserve.AdminReserveListDTO(g.name, g.addr, gm.businessId, gm.businessName ,r.reserveDate, r.time, r.createDate, r.price, m.name, m.email) " +
            "FROM Reserve r " +
            "JOIN r.ground g " +
            "JOIN g.member gm " +
            "JOIN r.member m ")
    Page<AdminReserveListDTO> findAllReserveList(Pageable pageable);
}