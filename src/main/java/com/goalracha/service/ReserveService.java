package com.goalracha.service;

import com.goalracha.dto.reserve.ReservDTO;
import com.goalracha.dto.reserve.ReserveListDTO;
import com.goalracha.dto.reserve.UserReserveListDTO;
import com.goalracha.entity.Reserve;

import java.util.List;
import java.util.Map;

public interface ReserveService {
    List<ReserveListDTO> getList();

    Reserve getOne(Long gno);

    ReserveListDTO getGroundReserve(Long gno);

    Reserve newReserve(ReservDTO reservDTO);

    Map<String, Object> getAllList(String date, String time, List<String> inout, String search);

    Map<String, Object> showGroundInfo(Long gno, String date);

    List<UserReserveListDTO>  getUserReserve(Long uNo);
}
