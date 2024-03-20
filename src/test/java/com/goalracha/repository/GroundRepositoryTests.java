package com.goalracha.repository;

import java.util.Optional;
import java.util.UUID;

import com.goalracha.dto.GroundDTO;
import com.goalracha.dto.PageRequestDTO;
import com.goalracha.entity.Ground;
import com.goalracha.service.GroundService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
public class GroundRepositoryTests {
    @Autowired
    GroundRepository groundRepository;
    @Autowired
    GroundService groundService;
    @Autowired
    ModelMapper modelMapper;

    @Test
    public void ListTest() {
        Long uNo = 3L;
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(), Sort.by(Sort.Direction.DESC, "gNo"));
        Page<Object[]> result = groundRepository.selectList(uNo, pageable);
        log.info(result);
    }

    @Test
    public void testInsert() {
        Ground ground = Ground.builder().addr("addr").airconIsYn(true).ballIsYn(true).closeTime(22).fare(40000L)
                .footwearIsYn(false).grassInfo("떼깔죽임").inAndOut("실내").name("김창욱").openTime(6).parkareaIsYn(true).recommdMan("6:6")
                .refundRules("환불규정").roopIsYn(true).showerIsYn(true).usageTime(2).userGuide("이용안내").userRules("이용규칙")
                .vestIsYn(true).width("30x40").build();
        // 이미지 파일 추가
        ground.registerImageName(UUID.randomUUID().toString() + " - " + "IMAGE1.jpg");
        ground.registerImageName(UUID.randomUUID().toString() + " - " + "IMAGE2.jpg");
        groundRepository.save(ground);
        log.info(" ------------------- ");
    }

//    @Test
//    public void testRead2() {
//        Long gno = 1L;
//        Optional<Ground> result = groundRepository.selectOne(gno);
//        Ground ground = result.orElseThrow();
//        log.info(ground);
//        log.info(ground.getImageList());
//    }

    @Commit
    @Transactional
    @Test
    public void testState() {
        Long gno = 3L;
        groundRepository.groundState(gno, 1L);
    }

    @Test
    public void readTest() {
        Long gNo = 2L;

        GroundDTO groundDTO = groundService.get((gNo));

        log.info(groundDTO);
        log.info(groundDTO.getUploadFileNames());
    }

}