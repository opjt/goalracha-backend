package com.goalracha.client.ground.service;


import com.goalracha.client.ground.dto.GroundDTO;
import com.goalracha.client.ground.dto.PageRequestDTO;
import com.goalracha.client.ground.dto.PageResponseDTO;
import com.goalracha.domain.Ground;
import com.goalracha.repository.GroundRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class GroundServiceImpl implements GroundService {

    private final ModelMapper modelMapper;
    private final GroundRepository groundRepository;
    @Override
    public Long register(GroundDTO groundDTO) {
        log.info("------------------");

        Ground ground = modelMapper.map(groundDTO, Ground.class);
        Ground savedGround = groundRepository.save(ground);

        return savedGround.getGno();
    }

    @Override
    public GroundDTO get(Long gno) {
        java.util.Optional<Ground> result = groundRepository.findById(gno);

        Ground ground = result.orElseThrow();
        GroundDTO dto = modelMapper.map(ground, GroundDTO.class);

        return dto;
    }

    @Override
    public void modify(GroundDTO groundDTO) {
        Optional<Ground> result = groundRepository.findById(groundDTO.getGno());

        Ground ground = result.orElseThrow();

        ground.changeName(ground.getName());
        ground.changeAddr(ground.getAddr());
        ground.changeInAndOut(ground.getInAndOut());
        ground.changeWidth(ground.getWidth());
        ground.changeGrassInfo(ground.getGrassInfo());
        ground.changeRecommdMan(ground.getRecommdMan());
        ground.changeUsageTime(ground.getUsageTime());
        ground.changeOpentime(ground.getOpenTime());
        ground.changeCloseTime(ground.getCloseTime());
        ground.changeFare(ground.getFare());
        ground.changeUserGuide(ground.getUserGuide());
        ground.changeUserRules(ground.getUserRules());
        ground.changeRefundRules(ground.getRefundRules());
        ground.changeChangeRules(ground.getChangeRules());
        ground.changeVestIsYn(ground.isVestIsYn());
        ground.changeFootwearIsYn(ground.isFootwearIsYn());
        ground.changeShowerIsYn(ground.isShowerIsYn());
        ground.changeBallIsYn(ground.isBallIsYn());
        ground.changeAirconIsYn(ground.isAirconIsYn());
        ground.changeParkareaIsYn(ground.isParkareaIsYn());
        ground.changeRoopIsYn(ground.isRoopIsYn());
        ground.changeState(ground.getState());

        groundRepository.save(ground);
    }

    @Override
    public void delete(Long gno) {
        groundRepository.deleteById(gno);
    }

    @Override
    public PageResponseDTO<GroundDTO> list(PageRequestDTO pageRequestDTO) {
        int page = pageRequestDTO.getPage();
        int size = pageRequestDTO.getSize();

        // Oracle의 rownum을 사용하기 위해 offset과 limit을 계산
        int offset = (page - 1) * size + 1;
        int endRowNum = offset + size - 1;

        // 정렬 방식 설정
        Sort sort = Sort.by(Sort.Direction.DESC, "gno");

        // 페이징 및 정렬된 결과 가져오기
        List<Ground> resultList = groundRepository.findWithPaging(offset, endRowNum);

        // DTO로 변환
        List<GroundDTO> dtoList = resultList.stream()
                .map(ground -> modelMapper.map(ground, GroundDTO.class))
                .collect(Collectors.toList());

        // 전체 엔터티 개수 가져오기
        long totalCount = groundRepository.count();

        // 응답 DTO 생성
        PageResponseDTO<GroundDTO> responseDTO = PageResponseDTO.<GroundDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();

        return responseDTO;
    }

    @Override
    public PageResponseDTO<GroundDTO> list2(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1, // 1페이지가 0이므로 주의
                pageRequestDTO.getSize(), Sort.by("tno").descending());

        Page<Ground> result = groundRepository.findAll(pageable);

        List<GroundDTO> dtoList = result.getContent().stream().map(todo -> modelMapper.map(todo, GroundDTO.class)).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        PageResponseDTO<GroundDTO> responseDTO =PageResponseDTO.<GroundDTO>withAll()
                .dtoList(dtoList).pageRequestDTO(pageRequestDTO).totalCount(totalCount).build();

        return responseDTO;
    }


}
