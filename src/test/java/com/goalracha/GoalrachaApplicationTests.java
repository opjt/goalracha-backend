package com.goalracha;

import com.goalracha.client.ground.dto.GroundDTO;
import com.goalracha.client.ground.service.GroundService;
import com.goalracha.domian.Ground;
import com.goalracha.repository.GroundRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Log4j2
@SpringBootTest
public class GoalrachaApplicationTests {
@Autowired
private GroundRepository groundRepository;
@Autowired
private GroundService groundService;

    @Test
    public  void test(){
        log.info("-----------------");
        log.info(groundRepository);

    }

    // GroundRepository test
    @Test
    public void groundInsert() {
        for (int i = 1; i < 10; i++) {
            Ground ground = Ground.builder().addr("addr"+i).airconIsYn(true).ballIsYn(true).changeRules("교환규정").closeTime("22시").fare(40000L)
                    .footwearIsYn(false).grassInfo("떼깔죽임").inAndOut("실내").name("김창욱").openTime("06시").parkareaIsYn(true).recommdMan("6:6")
                    .refundRules("환불규정").roopIsYn(true).showerIsYn(true).state(1L).uno(1L).usageTime(2L).userGuide("이용안내").userRules("이용규칙")
                    .vestIsYn(true).width("30x40").build();

            groundRepository.save(ground);
        }
    }

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
    public void testRsgister() {
        GroundDTO groundDTO = GroundDTO.builder().addr("가산디지털3로 332").airconIsYn(true).ballIsYn(true).changeRules("교환규정").closeTime("22시").fare(40000L)
                .footwearIsYn(false).grassInfo("떼깔죽임").inAndOut("실내").name("황은지").openTime("06시").parkareaIsYn(true).recommdMan("6:6")
                .refundRules("환불규정").roopIsYn(true).showerIsYn(true).state(1L).uno(1L).usageTime(2L).userGuide("이용안내").userRules("이용규칙")
                .vestIsYn(true).width("30x40").build();

        Long gno = groundService.register(groundDTO);
        log.info("gno: " + gno);
    }

    @Test
    public void testGet() {
        Long gno = 1L;
        GroundDTO groundDTO = groundService.get(gno);
        log.info(groundDTO);
    }
}
