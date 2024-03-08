package com.goalracha.service;

import com.goalracha.dto.reserve.ReservDTO;
import com.goalracha.dto.reserve.ReserveListDTO;
import com.goalracha.entity.Reserve;

import java.util.List;

public interface ReserveService {
    List<ReserveListDTO> getList();

    Reserve getOne(Long gno);

    ReserveListDTO getGroundReserve(Long gno);

    Reserve newReserve(ReservDTO reservDTO);
}
