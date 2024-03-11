package com.goalracha.service;

import com.goalracha.entity.Member;
import com.goalracha.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void changePassword(String userId, String newPassword) {
        Member member = memberRepository.findByUserId(userId);
        if (member == null) {
            throw new UsernameNotFoundException("User not found with userId: " + userId);
        }
        log.info("service " + member.toString());
        String encodedPassword = passwordEncoder.encode(newPassword);
        member.setPassword(encodedPassword);
        Member result = memberRepository.save(member);
        log.info("service result" + result.toString());

        System.out.println("Encoded password: " + encodedPassword);
    }
}