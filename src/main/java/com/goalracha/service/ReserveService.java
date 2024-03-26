package com.goalracha.service;

import com.goalracha.dto.PageRequestDTO;
import com.goalracha.dto.PageResponseDTO;
import com.goalracha.dto.reserve.*;
import com.goalracha.entity.Reserve;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReserveService {
    List<ReserveListDTO> getList();

    Reserve getOne(Long gno);

    ReserveListDTO getGroundReserve(Long gno);

    Map<String, Object> newReserve(ReserveAddsDTO reserveAddsDTO);

    Map<String, Object> getAllList(String date, String time, List<String> inout, String search);

    Map<String, Object> showGroundInfo(Long gno, String date);

    PageResponseDTO<UserReserveListDTO> getUserPreviousReservations(Long uNo, PageRequestDTO pageRequestDTO);

    // 유저 예약 현황 리스트
    PageResponseDTO<UserReserveListDTO> getUserReservationStatus(Long uNo, PageRequestDTO pageRequestDTO);

    // 사업자 예약 리스트
    PageResponseDTO<OwnerReserveListDTO> getOwnerReserveList(Long uNo, PageRequestDTO pageRequestDTO);

    // 전체 예약 리스트(관리자)
    PageResponseDTO<AdminReserveListDTO> getAllReserveList(PageRequestDTO pageRequestDTO);

    Map<String, Object> cancel(String header, String payKey, Long uNo);
    // 사업자 예약 리스트 조회 (구장명, 고객명)
    PageResponseDTO<OwnerReserveListDTO> getOwnerReserveListSearch(Long uNo, String searchName, PageRequestDTO pageRequestDTO);

    // 관리자 예약 리스트 조회 (구장명, 고객명)
    PageResponseDTO<AdminReserveListDTO> getAllReserveListSearch(String searchName, PageRequestDTO pageRequestDTO);

    Map<String, Object> infoByPayKey(String payKey);
    
    // 사업자 통계 리스트
    List<OwnerReserveListDTO> getOwnerStatistics(Long uNo);

    PageResponseDTO<UserReserveListDTO> getUserReservationsWithUserInfo(Long uNo, PageRequestDTO pageRequestDTO);

}
