package com.goalracha.service;


import com.goalracha.dto.GroundDTO;
import com.goalracha.dto.PageRequestDTO;
import com.goalracha.dto.PageResponseDTO;
import com.goalracha.entity.Ground;
import com.goalracha.entity.GroundImage;
import com.goalracha.entity.Member;
import com.goalracha.repository.GroundRepository;
import com.goalracha.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    @Override
    public PageResponseDTO<GroundDTO> listWithImageByUno(Long uNo, PageRequestDTO pageRequestDTO) {
        log.info("ground list");

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by(Sort.Direction.DESC, "gNo"));
        Page<Object[]> result = groundRepository.selectOnwerList(uNo, pageable);

        int offset = pageable.getPageNumber() * pageable.getPageSize() + 1; // offset 계산에서 +1
        int limit = offset + pageable.getPageSize() - 1;

        List<GroundDTO> dtoList = result.get().map(arr -> {
            Ground ground = (Ground) arr[0];
            GroundImage groundImage = (GroundImage) arr[1];

            GroundDTO groundDTO = GroundDTO.builder().gNo(ground.getGNo()).name(ground.getName()).addr(ground.getAddr())
                    .inAndOut(ground.getInAndOut()).width(ground.getWidth()).grassInfo(ground.getGrassInfo())
                    .recommdMan(ground.getRecommdMan()).usageTime(ground.getUsageTime()).openTime(ground.getOpenTime())
                    .closeTime(ground.getCloseTime()).fare(ground.getFare()).userGuide(ground.getUserGuide())
                    .userRules(ground.getUserRules()).refundRules(ground.getRefundRules()).vestIsYn(ground.isVestIsYn())
                    .footwearIsYn(ground.isFootwearIsYn()).showerIsYn(ground.isShowerIsYn()).roopIsYn(ground.isRoopIsYn())
                    .airconIsYn(ground.isAirconIsYn()).parkareaIsYn(ground.isParkareaIsYn()).state(ground.getState()).build();
            String imageStr = groundImage.getFileDirectory();
            groundDTO.setUploadFileNames(List.of(imageStr));

            return groundDTO;
        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDTO.<GroundDTO>withAll().dtoList(dtoList).totalCount(totalCount).pageRequestDTO(pageRequestDTO).build();
    }

    @Override
    public PageResponseDTO<GroundDTO> listWithImage(PageRequestDTO pageRequestDTO) {
        log.info("ground list");

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by(Sort.Direction.DESC, "gNo"));
        Page<Object[]> result = groundRepository.selectList(pageable);

        int offset = pageable.getPageNumber() * pageable.getPageSize() + 1; // offset 계산에서 +1
        int limit = offset + pageable.getPageSize() - 1;

        List<GroundDTO> dtoList = result.get().map(arr -> {
            Ground ground = (Ground) arr[0];
            GroundImage groundImage = (GroundImage) arr[1];

            GroundDTO groundDTO = GroundDTO.builder().gNo(ground.getGNo()).name(ground.getName()).addr(ground.getAddr())
                    .inAndOut(ground.getInAndOut()).width(ground.getWidth()).grassInfo(ground.getGrassInfo())
                    .recommdMan(ground.getRecommdMan()).usageTime(ground.getUsageTime()).openTime(ground.getOpenTime())
                    .closeTime(ground.getCloseTime()).fare(ground.getFare()).userGuide(ground.getUserGuide())
                    .userRules(ground.getUserRules()).refundRules(ground.getRefundRules()).vestIsYn(ground.isVestIsYn())
                    .footwearIsYn(ground.isFootwearIsYn()).showerIsYn(ground.isShowerIsYn()).roopIsYn(ground.isRoopIsYn())
                    .airconIsYn(ground.isAirconIsYn()).parkareaIsYn(ground.isParkareaIsYn()).state(ground.getState()).build();
            String imageStr = groundImage.getFileDirectory();
            groundDTO.setUploadFileNames(List.of(imageStr));

            return groundDTO;
        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDTO.<GroundDTO>withAll().dtoList(dtoList).totalCount(totalCount).pageRequestDTO(pageRequestDTO).build();
    }

    @Override
    public PageResponseDTO<GroundDTO> listWithImageSearchByUno(Long uNo, String searchName, PageRequestDTO pageRequestDTO) {
        log.info("ground list");

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by(Sort.Direction.DESC, "gNo"));
        Page<Object[]> result = groundRepository.selectOnwerListSearch(uNo, searchName, pageable);

        int offset = pageable.getPageNumber() * pageable.getPageSize() + 1; // offset 계산에서 +1
        int limit = offset + pageable.getPageSize() - 1;

        List<GroundDTO> dtoList = result.get().map(arr -> {
            Ground ground = (Ground) arr[0];
            GroundImage groundImage = (GroundImage) arr[1];

            GroundDTO groundDTO = GroundDTO.builder().gNo(ground.getGNo()).name(ground.getName()).addr(ground.getAddr())
                    .inAndOut(ground.getInAndOut()).width(ground.getWidth()).grassInfo(ground.getGrassInfo())
                    .recommdMan(ground.getRecommdMan()).usageTime(ground.getUsageTime()).openTime(ground.getOpenTime())
                    .closeTime(ground.getCloseTime()).fare(ground.getFare()).userGuide(ground.getUserGuide())
                    .userRules(ground.getUserRules()).refundRules(ground.getRefundRules()).vestIsYn(ground.isVestIsYn())
                    .footwearIsYn(ground.isFootwearIsYn()).showerIsYn(ground.isShowerIsYn()).roopIsYn(ground.isRoopIsYn())
                    .airconIsYn(ground.isAirconIsYn()).parkareaIsYn(ground.isParkareaIsYn()).state(ground.getState()).build();
            String imageStr = groundImage.getFileDirectory();
            groundDTO.setUploadFileNames(List.of(imageStr));

            return groundDTO;
        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDTO.<GroundDTO>withAll().dtoList(dtoList).totalCount(totalCount).pageRequestDTO(pageRequestDTO).build();
    }

    @Override
    public Long register(GroundDTO groundDTO, Long uNo) {
        Member member = memberRepository.findById(uNo).orElseThrow(() -> new IllegalArgumentException("해당 uNo의 회원이 존재하지 않습니다. uNo=" + uNo));
        Ground ground = GroundDTO.dtoToEntity(groundDTO, member);
        Ground result = groundRepository.save(ground);
        return result.getGNo();
    }

    @Override
    public GroundDTO get(Long gNo) {
        java.util.Optional<Ground> result = groundRepository.selectOne(gNo);
        Ground ground = result.orElseThrow();
        GroundDTO groundDTO = GroundDTO.entityToDTO(ground);

        return groundDTO;

    }

    @Override
    public void modify(GroundDTO groundDTO) {
        Optional<Ground> result = groundRepository.findById(groundDTO.getGNo());

        Ground ground = result.orElseThrow();
        log.info(result);

        ground.changeName(groundDTO.getName());
        ground.changeAddr(groundDTO.getAddr());
        ground.changeInAndOut(groundDTO.getInAndOut());
        ground.changeWidth(groundDTO.getWidth());
        ground.changeGrassInfo(groundDTO.getGrassInfo());
        ground.changeRecommdMan(groundDTO.getRecommdMan());
        ground.changeUsageTime(groundDTO.getUsageTime());
        ground.changeOpentime(groundDTO.getOpenTime());
        ground.changeCloseTime(groundDTO.getCloseTime());
        ground.changeFare(groundDTO.getFare());
        ground.changeUserGuide(groundDTO.getUserGuide());
        ground.changeUserRules(groundDTO.getUserRules());
        ground.changeRefundRules(groundDTO.getRefundRules());
        ground.changeVestIsYn(groundDTO.isVestIsYn());
        ground.changeFootwearIsYn(groundDTO.isFootwearIsYn());
        ground.changeShowerIsYn(groundDTO.isShowerIsYn());
        ground.changeBallIsYn(groundDTO.isBallIsYn());
        ground.changeAirconIsYn(groundDTO.isAirconIsYn());
        ground.changeParkareaIsYn(groundDTO.isParkareaIsYn());
        ground.changeRoopIsYn(groundDTO.isRoopIsYn());
        ground.changeState(groundDTO.getState());

        ground.clearList();
        List<String> uploadFileNames = groundDTO.getUploadFileNames();
        if (uploadFileNames != null && uploadFileNames.size() > 0) {
            uploadFileNames.stream().forEach(uploadName -> {
                ground.registerImageName(uploadName);
            });
        }

        groundRepository.save(ground);
    }

    @Override
    public void delete(Long gno) {
        groundRepository.deleteById(gno);
    }

    @Override
    public void changeState(Long gNo, Long newState) {
        Ground ground = groundRepository.findById(gNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 구장이 존재하지 않습니다. gNo=" + gNo));
        ground.changeState(newState);
        groundRepository.save(ground);
    }

    @Override
    public List<String> findAllImageFileNamesByGNo(Long gNo) {
        return groundRepository.findAllImageFileNamesByGNo(gNo);
    }
}
