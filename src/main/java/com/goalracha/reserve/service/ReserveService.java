package com.goalracha.reserve.service;

import com.goalracha.reserve.dto.PageRequestDTO;
import com.goalracha.reserve.dto.PageResponseDTO;
import com.goalracha.reserve.dto.ReserveDTO;

public interface ReserveService {
    Long register (ReserveDTO reserveDTO);  // 예약 등록
    ReserveDTO get(Long rNo);   // 예약 조회
    void remove(Long rNo);  // 예약 삭제
    PageResponseDTO<ReserveDTO> list(PageRequestDTO pageRequestDTO);


}
