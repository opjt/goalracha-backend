package com.goalracha.service;

import com.goalracha.dto.reserve.ReservDTO;
import com.goalracha.dto.reserve.ReserveListDTO;
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
}
