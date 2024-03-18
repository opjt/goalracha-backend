package com.goalracha.service;

import com.goalracha.dto.PageResponseDTO;
import com.goalracha.dto.reserve.AdminReserveListDTO;
import com.goalracha.dto.reserve.ReservDTO;
import com.goalracha.dto.reserve.ReserveListDTO;
import com.goalracha.dto.reserve.UserReserveListDTO;
import com.goalracha.entity.Reserve;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReserveService {
    List<ReserveListDTO> getList();

    Reserve getOne(Long gno);

    ReserveListDTO getGroundReserve(Long gno);

    Reserve newReserve(ReservDTO reservDTO);

    Map<String, Object> getAllList(String date, String time, List<String> inout, String search);

    Map<String, Object> showGroundInfo(Long gno, String date);

    Map<String, Object> newReserve(Long gNo, Long uNo, Date date, String time);

    // 유저 이전 예약 리스트 페이지네이션 처리
    PageResponseDTO<UserReserveListDTO> getUserPreviousReservations(Long uNo, Pageable pageable);

    // 유저 예약 현황 리스트
    PageResponseDTO<UserReserveListDTO> getUserReservationStatus(Long uNo, Pageable pageable);

    // 사업자 예약 리스트
     PageResponseDTO<AdminReserveListDTO>getOwnerReserveList(Long uNo, Pageable pageable);

    // 전체 예약 리스트(관리자)
    PageResponseDTO<AdminReserveListDTO> getAllReserveList(Pageable pageable);


    boolean hasReservations(Long uNo); // 회원 탈퇴 (주어진 사용자번호 uno에 대해 예약내역이 있는지 여부를 확인)
}
