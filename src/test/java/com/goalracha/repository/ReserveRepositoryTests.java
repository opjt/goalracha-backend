package com.goalracha.repository;

import com.goalracha.dto.PageResponseDTO;
import com.goalracha.dto.reserve.AdminReserveListDTO;
import com.goalracha.dto.reserve.UserReserveListDTO;
import com.goalracha.entity.Ground;
import com.goalracha.entity.Member;
import com.goalracha.entity.Reserve;
import com.goalracha.service.ReserveService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Transactional
@Log4j2
public class ReserveRepositoryTests {


    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private ReserveService reserveService;

    @Test
    public void InsertData() {
        Member meber = Member.builder().uNo((long) 22).build();
        Ground gr = Ground.builder().gNo((long) 1).build();
        Reserve reserve = Reserve.builder().state(0).reserveDate(new Date()).ground(gr).member(meber).build();
        log.info(reserve);

        Reserve result = reserveRepository.save(reserve);
        log.info(result);


    }

    @Test
    public void Read() {
        Reserve reserve = reserveRepository.findById((long) 5).orElse(null);
        log.info(reserve.getGround());
    }

/*
    @Test   // user 이전 에약 목록 테스트
    public void testUserPreviousReservations() {
        // 사용자 번호
        Long uNo = 178L;

        // 페이지 번호와 페이지 크기 설정
        int pageNumber = 0; // 페이지 번호
        int pageSize = 10; // 페이지 크기

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // 서비스 메서드 호출
        PageResponseDTO<UserReserveListDTO> result = reserveService.getUserPreviousReservations(uNo, pageable);


        // 결과 출력
        log.info(result);
    }

    @Test   // user 예약현황 목록 테스트
    public void testUserReservationStatus() {
        // 사용자 번호
        Long uNo = 178L;

        // 페이지 번호와 페이지 크기 설정
        int pageNumber = 0; // 페이지 번호
        int pageSize = 10; // 페이지 크기

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // 서비스 메서드 호출
        PageResponseDTO<UserReserveListDTO> result = reserveService.getUserReservationStatus(uNo, pageable);


        // 결과 출력
        log.info(result);
    }


    @Test   // owner 에약 목록 테스트
    public void testOwnerReserveList() {
        // 사용자 번호
        Long uNo = 142L;

        // 페이지 번호와 페이지 크기 설정
        int pageNumber = 0; // 페이지 번호
        int pageSize = 10; // 페이지 크기

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // 서비스 메서드 호출
        PageResponseDTO<AdminReserveListDTO> result = reserveService.getOwnerReserveList(uNo, pageable);


        // 결과 출력
        log.info(result);
    }

    @Test   // admin 전체 예약 목록 테스트
    public void testGetAllReserveList() {

        // 페이지 번호와 페이지 크기 설정
        int pageNumber = 0; // 페이지 번호
        int pageSize = 10; // 페이지 크기

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // 서비스 메서드 호출
        PageResponseDTO<AdminReserveListDTO> result = reserveService.getAllReserveList(pageable);


        // 결과 출력
        log.info(result);
    }
    */

}
