package com.goalracha.service;


import com.goalracha.dto.GroundDTO;
import com.goalracha.dto.MemberDTO;
import com.goalracha.dto.PageRequestDTO;
import com.goalracha.dto.PageResponseDTO;
import com.goalracha.entity.Ground;
import com.goalracha.entity.GroundImage;
import com.goalracha.entity.Member;
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

import java.util.ArrayList;
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

//    public GroundDTO groundToDTO(Ground ground) {
//        GroundDTO dto = new GroundDTO();
//        dto.setGNo(ground.getGNo());
//        dto.setName(ground.getName());
//        dto.setAddr(ground.getAddr());
//        dto.setInAndOut(ground.getInAndOut());
//        dto.setWidth(ground.getWidth());
//        dto.setGrassInfo(ground.getGrassInfo());
//        dto.setRecommdMan(ground.getRecommdMan());
//        dto.setUsageTime(ground.getUsageTime());
//        dto.setOpenTime(ground.getOpenTime());
//        dto.setCloseTime(ground.getCloseTime());
//        dto.setFare(ground.getFare());
//        dto.setUserGuide(ground.getUserGuide());
//        dto.setUserRules(ground.getUserRules());
//        dto.setRefundRules(ground.getRefundRules());
//        dto.setVestIsYn(ground.isVestIsYn());
//        dto.setFootwearIsYn(ground.isFootwearIsYn());
//        dto.setShowerIsYn(ground.isShowerIsYn());
//        dto.setBallIsYn(ground.isBallIsYn());
//        dto.setAirconIsYn(ground.isAirconIsYn());
//        dto.setParkareaIsYn(ground.isParkareaIsYn());
//        dto.setRoopIsYn(ground.isRoopIsYn());
//
//        List<String> imageNames = new ArrayList<>();
//        for (GroundImage image : ground.getImageList()) {
//            imageNames.add(image.getFileDirectory());
//        }
//        dto.setImageNames(imageNames);
//
//        return dto;
//    }
//
//    public MemberDTO convertToDTO(Member member) {
//        MemberDTO memberDTO = modelMapper.map(member, MemberDTO.class);
//        return memberDTO;
//    }
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
                    .airconIsYn(ground.isAirconIsYn()).parkareaIsYn(ground.isParkareaIsYn()).build();
            String imageStr = groundImage.getFileDirectory();
            groundDTO.setUploadFileNames(List.of(imageStr));

            return groundDTO;
        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDTO.<GroundDTO>withAll().dtoList(dtoList).totalCount(totalCount).pageRequestDTO(pageRequestDTO).build();
    }
@Override
public Long register(GroundDTO groundDTO) {
    Ground ground = dtoToEntity(groundDTO);
    Ground result = groundRepository.save(ground);
    return result.getGNo();
}
    private Ground dtoToEntity(GroundDTO groundDTO) {
        Ground ground = Ground.builder().gNo(groundDTO.getGNo()).name(groundDTO.getName()).addr(groundDTO.getAddr())
                .inAndOut(groundDTO.getInAndOut()).width(groundDTO.getWidth()).grassInfo(groundDTO.getGrassInfo())
                .recommdMan(groundDTO.getRecommdMan()).usageTime(groundDTO.getUsageTime()).openTime(groundDTO.getOpenTime())
                .closeTime(groundDTO.getCloseTime()).fare(groundDTO.getFare()).userGuide(groundDTO.getUserGuide())
                .userRules(groundDTO.getUserRules()).refundRules(groundDTO.getRefundRules()).vestIsYn(groundDTO.isVestIsYn())
                .footwearIsYn(groundDTO.isFootwearIsYn()).showerIsYn(groundDTO.isShowerIsYn()).roopIsYn(groundDTO.isRoopIsYn())
                .airconIsYn(groundDTO.isAirconIsYn()).parkareaIsYn(groundDTO.isParkareaIsYn())
                .build();

        List<String> uploadFileNames = groundDTO.getUploadFileNames();
        if (uploadFileNames == null) {
            return ground;
        }
        uploadFileNames.stream().forEach(uploadName -> {
            ground.registerImageName(uploadName);
        });
        return ground;
    }

    @Override
    public GroundDTO get(Long gNo) {
        java.util.Optional<Ground> result = groundRepository.selectOne(gNo);
        Ground ground = result.orElseThrow();
        GroundDTO groundDTO = entityToDTO(ground);

        return groundDTO;
    }
    private GroundDTO entityToDTO(Ground ground) {
        GroundDTO groundDTO = GroundDTO.builder().gNo(ground.getGNo()).name(ground.getName()).addr(ground.getAddr())
                .inAndOut(ground.getInAndOut()).width(ground.getWidth()).grassInfo(ground.getGrassInfo())
                .recommdMan(ground.getRecommdMan()).usageTime(ground.getUsageTime()).openTime(ground.getOpenTime())
                .closeTime(ground.getCloseTime()).fare(ground.getFare()).userGuide(ground.getUserGuide())
                .userRules(ground.getUserRules()).refundRules(ground.getRefundRules()).vestIsYn(ground.isVestIsYn())
                .footwearIsYn(ground.isFootwearIsYn()).showerIsYn(ground.isShowerIsYn()).roopIsYn(ground.isRoopIsYn())
                .airconIsYn(ground.isAirconIsYn()).parkareaIsYn(ground.isParkareaIsYn()).build();
        List<GroundImage> imageList = ground.getImageList();
        if (imageList == null || imageList.size() == 0) {

            return groundDTO;
        }

        List<String> fileNameList = imageList.stream().map(productImage ->
                productImage.getFileDirectory()).toList();
        groundDTO.setUploadFileNames(fileNameList);

        return groundDTO;

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
        ground.changeVestIsYn(ground.isVestIsYn());
        ground.changeFootwearIsYn(ground.isFootwearIsYn());
        ground.changeShowerIsYn(ground.isShowerIsYn());
        ground.changeBallIsYn(ground.isBallIsYn());
        ground.changeAirconIsYn(ground.isAirconIsYn());
        ground.changeParkareaIsYn(ground.isParkareaIsYn());
        ground.changeRoopIsYn(ground.isRoopIsYn());
        ground.changeState(ground.getState());

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

}
