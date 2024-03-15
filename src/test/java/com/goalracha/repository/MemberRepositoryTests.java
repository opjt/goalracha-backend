package com.goalracha.repository;

import com.goalracha.entity.Member;
import com.goalracha.entity.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest
@Log4j2
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsertAdmin() {
        Member member = Member.builder().email("admin@naver.com").pw(passwordEncoder.encode("1234")).type(MemberRole.ADMIN).
                userId("admin").
                nickname("admin").build();
        Member save = memberRepository.save(member);
        log.info(save);

    }
    @Test
    public void testRead() {
        String id = "admin";
        Member member = memberRepository.findByUserId(id);
        log.info(member);
    }

}
