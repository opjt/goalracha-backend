package com.goalracha.service;

import com.goalracha.dto.GroundDTO;
import com.goalracha.dto.PageRequestDTO;
import com.goalracha.dto.PageResponseDTO;
import com.goalracha.entity.GroundImage;
import java.util.List;

public interface GroundService {

    // 사용자별로 이미지와 함께 페이지별 구장 목록 조회
    PageResponseDTO<GroundDTO> listWithImageByUno(Long uNo, PageRequestDTO pageRequestDTO);

    // 이미지와 함께 페이지별 구장 목록 조회
    PageResponseDTO<GroundDTO> listWithImage(PageRequestDTO pageRequestDTO);

    // 사용자별로 이미지와 함께 검색된 페이지별 구장 목록 조회
    PageResponseDTO<GroundDTO> listWithImageSearchByUno(Long uNo, String searchName, PageRequestDTO pageRequestDTO);

    // 구장 등록
    Long register(GroundDTO groundDTO, Long uNo);

    // 구장 상세 정보 조회
    GroundDTO get(Long gNo);

    // 구장 정보 수정
    void modify(GroundDTO groundDTO);

    // 구장 삭제
    void delete(Long gNo);

    // 관리자 페이지에서 구장 상태 변경
    void changeState(Long gNo, Long newState);

    // 특정 구장의 모든 이미지 파일 이름 목록 조회
    List<String> findAllImageFileNamesByGNo(Long gNo);

}
