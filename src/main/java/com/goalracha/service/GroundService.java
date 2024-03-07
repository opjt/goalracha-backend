package com.goalracha.service;

import com.goalracha.dto.GroundDTO;
import com.goalracha.dto.PageRequestDTO;
import com.goalracha.dto.PageResponseDTO;

public interface GroundService {

    // 구장 등록
    Long register(GroundDTO groundDTO);

    // 구장 조회
    GroundDTO get(Long gno);

    // 구장 수정
    void modify(GroundDTO groundDTO);

    // 구장 삭제
    void delete(Long gno);

    // 구장관리 페이지 처리
    PageResponseDTO<GroundDTO> list(PageRequestDTO pageRequestDTO);

}
