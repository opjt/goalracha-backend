package com.goalracha.repository;

import com.goalracha.entity.Ground;
import com.goalracha.entity.Member;
import com.goalracha.entity.Reserve;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@Transactional
@Log4j2
public class ReserveRepositoryTests {
    @Autowired
    private ReserveRepository reserveRepository;

    @Test
    public void InsertData() {
        Member meber = Member.builder().uNo((long)22).build();
        Ground gr = Ground.builder().gNo((long)1).build();
        Reserve reserve = Reserve.builder().state(0).reserveDate(new Date()).ground(gr).member(meber).build();
        log.info(reserve);

        Reserve result = reserveRepository.save(reserve);
        log.info(result);


    }
    @Test
    public void Read() {
        Reserve reserve = reserveRepository.findById( (long)5).orElse(null);
        log.info(reserve.getGround());



    }

}
