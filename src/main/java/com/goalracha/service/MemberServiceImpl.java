package com.goalracha.service;

import com.goalracha.domain.Member;
import com.goalracha.domain.MemberRole;
import com.goalracha.dto.MemberDTO;
import com.goalracha.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDTO getKakaoMember(String accessToken) {
        String email = getEmailFromKakaoAccessToken(accessToken);
        log.info("email: " + email);
        Member result = memberRepository.findByEmail(email);
        // 기존의 회원
        if (result != null) {
            MemberDTO memberDTO = MemberDTO.entityToDTO(result);
            return memberDTO;
        }
        log.info("유저 생성합니다");

        Member member = createKkoMember(email);
        memberRepository.save(member);
        log.info(member.toString());
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
    private Member createKkoMember(String email) { //카카오 유저 빌더

        Member member = Member.builder().email(email).userId(email).nickname(email).pw("null").type(MemberRole.USER).createDate(new Date()).state(1).build();
        return member;
    }

    private String getEmailFromKakaoAccessToken(String accessToken) { //카카오토큰으로 유저 이메일 리턴
        String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";
        if (accessToken == null) {
            throw new RuntimeException("Access Token is null");
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoGetUserURL).build();
        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(uriBuilder.toString(), HttpMethod.GET, entity,
                LinkedHashMap.class);
        log.info(response);
        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();
        log.info("------------------------------------");
        log.info(bodyMap);
        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("kakao_account");
        log.info("kakaoAccount: " + kakaoAccount);
        return kakaoAccount.get("email");
    }

}
