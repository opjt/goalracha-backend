package com.goalracha.service;

import com.goalracha.dto.GroundDTO;
import com.goalracha.dto.PageRequestDTO;
import com.goalracha.dto.PageResponseDTO;
import com.goalracha.entity.GroundImage;
import java.util.List;


public interface GroundService {

    PageResponseDTO<GroundDTO> listWithImage2(Long uNo, PageRequestDTO pageRequestDTO);

    PageResponseDTO<GroundDTO> listWithImage(PageRequestDTO pageRequestDTO);

    // 구장 등록
    Long register(GroundDTO groundDTO, Long uNo);

    // 구장 조회
    GroundDTO get(Long gNo);

    // 구장 수정
    void modify(GroundDTO groundDTO);

    // 구장 삭제
    void delete(Long gNo);

    // 관리자페이지 상태변경
    void changeState(Long gNo, Long newState);

    List<String> findAllImageFileNamesByGNo(Long gNo);

}
