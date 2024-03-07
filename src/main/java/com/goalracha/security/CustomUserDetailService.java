package com.goalracha.security;

import com.goalracha.entity.Member;
import com.goalracha.dto.MemberDTO;
import com.goalracha.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        log.info("----------------loadUserByUsername-------------------------");
        log.info(id);
        Member member = memberRepository.findByUserId(id);
        if (member == null) {
            throw new UsernameNotFoundException("Not Found");
        }
        MemberDTO memberDTO = new MemberDTO(
                member.getUNo(),
                member.getUserId(),
                member.getPw(),
                member.getNickname(),
                member.getName(),
                member.getTel(),
                member.getCreateDate(),
                member.getEmail(),
                member.getBusinessName(),
                member.getBusinessId(),
                member.getState(),
                member.getType());
        return memberDTO;
    }
}