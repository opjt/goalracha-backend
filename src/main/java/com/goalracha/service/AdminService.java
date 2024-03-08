package com.goalracha.service;

import com.goalracha.domain.Member;
import com.goalracha.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AdminService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void changePassword(String userId, String newPassword) {
        Member member = memberRepository.findByUserId(userId);
        if (member == null) {
            throw new UsernameNotFoundException("User not found with userId: " + userId);
        }
        log.info("service " + member.toString());
        String encodedPassword = passwordEncoder.encode(newPassword);
        member.setPassword(encodedPassword);
        Member result = memberRepository.save(member);
        log.info("service  result" + result.toString());

        System.out.println("Encoded password: " + encodedPassword);
    }
}