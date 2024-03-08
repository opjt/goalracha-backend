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

        return savedGround.getGNo();
    }

    @Override
    public GroundDTO get(Long gNo) {
        java.util.Optional<Ground> result = groundRepository.findById(gNo);

        Ground ground = result.orElseThrow();
        GroundDTO dto = modelMapper.map(ground, GroundDTO.class);

        return dto;
    }

    @Override
    public void modify(GroundDTO groundDTO) {
        Optional<Ground> result = groundRepository.findById(groundDTO.getGNo());

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
    public void remove(Long gNo) {
        groundRepository.deleteById(gNo);
    }

    @Override
    public PageResponseDTO<GroundDTO> list(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,    // 1페이지는 0이여서 -1
                pageRequestDTO.getSize(),
                Sort.by(Sort.Direction.DESC, "gNo")
        );

        Page<Ground> result = groundRepository.findAll(pageable);

        List<GroundDTO> dtoList = result.getContent().stream()
                .map(ground -> modelMapper.map(ground, GroundDTO.class)).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        PageResponseDTO<GroundDTO> responseDTO = PageResponseDTO.<GroundDTO>withAll()
                .dtoList(dtoList).pageRequestDTO(pageRequestDTO).totalCount(totalCount).build();

        return responseDTO;
    }

}
