package com.goalracha.repository;

import com.goalracha.domain.Ground;
import com.goalracha.domain.Member;
import com.goalracha.domain.Reserve;
import com.goalracha.reserve.dto.PageRequestDTO;
import com.goalracha.reserve.dto.PageResponseDTO;
import com.goalracha.reserve.dto.ReserveDTO;
import com.goalracha.reserve.service.ReserveService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
public class ReserveRepositoryTests {

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private ReserveService reserveService;

    @Autowired
    GroundRepository groundRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(2).size(10).build();
        PageResponseDTO<ReserveDTO> response = reserveService.list(pageRequestDTO);
        log.info(response);
    }

    @Test
    public void testGet() {
        Long rNo = 99L;
        ReserveDTO reserveDTO = reserveService.get(rNo);
        log.info(reserveDTO);
    }

/*    @Test
    public void testRegister() {
        ReserveDTO reserveDTO = ReserveDTO.builder().time("14~16").reservationDate(LocalDate.now()).createDate(LocalDate.now()).state(1).paymentType(1)
                .gNo(1L).uNo(1L).build();

        Long rNo = reserveService.register(reserveDTO);
        log.info("r_no = " + rNo);
    }*/

    @Test
    public void testPaging() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("rNo").descending());
        Page<Reserve> result = reserveRepository.findAll(pageable);
        log.info(result.getTotalElements());
        result.getContent().stream().forEach(reserve -> log.info(reserve));
    }


    @Test
    public void testDeleteReserve() {
        Long rNo = 1L;
        reserveRepository.deleteById(1L);
    }

    @Test
    public void testReadReserve() {
        Long rNo = 33L;
        java.util.Optional<Reserve> result = reserveRepository.findById(rNo);
        Reserve reserve = result.orElseThrow();
        log.info(reserve);
    }

    /*@Test
    public void testInsertReserve() {

        Ground ground = groundRepository.findById(1L).orElse(null); // 구장 객체 가져오기
        Member member = memberRepository.findById(82L).orElse(null); // 유저 객체 가져오기

        if (ground != null && member != null) { // 구장과 유저가 존재하는 경우에만 예약 생성
            for (int i = 0; i < 100; i++) {
                Reserve reserve = Reserve.builder()
                        .time("10:00")  // 예약 시간
                        .reservationDate(LocalDate.now())  // 예약 날짜
                        .createDate(LocalDate.now())  // 생성일
                        .state(1)  // 예약 상태 (예약 완료)
                        .paymentType(1)  // 결제 유형
                        .ground(ground)  // 구장 객체 설정
                        .member(member)  // 유저 객체 설정
                        .build();
                reserveRepository.save(reserve);
            }
        } else {
            System.out.println("구장 또는 유저가 존재하지 않습니다.");
        }
    }*/

    @Test
    public void testReserveRepository() {
        log.info("---------");
        log.info(reserveRepository);
    }
}