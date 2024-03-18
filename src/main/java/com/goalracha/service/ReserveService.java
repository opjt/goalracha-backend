package com.goalracha.service;

import com.goalracha.dto.reserve.ReservDTO;
import com.goalracha.dto.reserve.ReserveListDTO;
import com.goalracha.dto.reserve.UserReserveListDTO;
import com.goalracha.entity.Reserve;

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

    List<UserReserveListDTO>  getUserReserve(Long uNo);

    boolean hasReservations(Long uNo); // 회원 탈퇴 (주어진 사용자번호 uno에 대해 예약내역이 있는지 여부를 확인)
}
