package com.goalracha.client.ground.service;

import com.goalracha.client.ground.dto.GroundDTO;

public interface GroundService {

    // 구장 등록
    Long register(GroundDTO groundDTO);

    // 구장 조회
    GroundDTO get(Long gno);

    // 구장 수정
    void modify(GroundDTO groundDTO);

    // 구장 삭제
    void remove(Long gno);
}
