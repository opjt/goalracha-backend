package com.goalracha.controller;

import com.goalracha.dto.MemberDTO;
import com.goalracha.service.MemberService;
import com.goalracha.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class KkoController {
    private final MemberService memberService;

    @GetMapping("/api/member/kakao")
    public Map<String, Object> getMemberFromKakao(String accessToken) {
        log.info("access Token ");
        log.info(accessToken);
        MemberDTO memberDTO = memberService.getKakaoMember(accessToken);
        Map<String, Object> claims = memberDTO.getClaims();
        String jwtAccessToken = JWTUtil.generateToken(claims, 10); //10분
        String jwtRefreshToken = JWTUtil.generateToken(claims, 60 * 24); //24시간
        claims.put("accessToken", jwtAccessToken);
        claims.put("refreshToken", jwtRefreshToken);
        return claims;
    }
}
