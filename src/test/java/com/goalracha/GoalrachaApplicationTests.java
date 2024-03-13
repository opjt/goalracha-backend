package com.goalracha;

import com.goalracha.dto.GroundDTO;
import com.goalracha.dto.PageRequestDTO;
import com.goalracha.dto.PageResponseDTO;
import com.goalracha.service.GroundService;
import com.goalracha.entity.Ground;
import com.goalracha.repository.GroundRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;


@Log4j2
@SpringBootTest
public class GoalrachaApplicationTests {
    @Autowired
    private GroundRepository groundRepository;
    @Autowired
    private GroundService groundService;

    @Test
    @Commit
    public  void test(){
        log.info("-----------------");

        Long groundNo = 57L; // 변경할 Ground의 ID
        Long newState = 2L; // 변경할 새로운 상태
        groundRepository.updateGroundState(groundNo, newState);

    }

    // GroundRepository test
//    @Test
//    public void groundInsert() {
//        for (int i = 1; i < 10; i++) {
//            Ground ground = Ground.builder().addr("addr"+i).airconIsYn(true).ballIsYn(true).changeRules("교환규정").closeTime(22).fare(40000L)
//                    .footwearIsYn(false).grassInfo("떼깔죽임").inAndOut("실내").name("김창욱").openTime(6).parkareaIsYn(true).recommdMan("6:6")
//                    .refundRules("환불규정").roopIsYn(true).showerIsYn(true).state(1L).uNo(1L).usageTime(2L).userGuide("이용안내").userRules("이용규칙")
//                    .vestIsYn(true).width("30x40").build();
//
//            groundRepository.save(ground);
//        }
//    }

    @Test
    public void groundRead() {
        Long gno = 3L;
        java.util.Optional<Ground> result=groundRepository.findById(gno);
        Ground ground = result.orElseThrow();
        log.info(ground);
    }

    @Test
    public void groundDelete() {
        Long gno = 4L;

        groundRepository.deleteById(gno);
    }

    @Test
    public void testPaging() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("g_no").descending());
        Page<Ground> result = groundRepository.findAll(pageable);
        log.info(result.getTotalElements());
        result.getContent().stream().forEach(ground -> log.info(ground));
    }

    // GroundSevice 테스트

    @Test
    public void testGet() {
        Long gno = 1L;
        GroundDTO groundDTO = groundService.get(gno);
        log.info(groundDTO);
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(2).size(10).build();

        PageResponseDTO<GroundDTO> response = groundService.list(pageRequestDTO);
        log.info(response);
    }


}
