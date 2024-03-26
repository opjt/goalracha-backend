package com.goalracha.controller;

import com.goalracha.dto.MemberDTO;
import com.goalracha.dto.MemberJoinDTO;
import com.goalracha.service.MemberService;
import com.goalracha.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class KkoController {
    private final MemberService memberService;

    @GetMapping("/api/member/g/kakao")
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
    @PutMapping("/api/member/{uNo}")
    public Map<String, String> modify(@PathVariable(name="uNo") Long uNo, @RequestBody MemberJoinDTO memberJoinDTO) {
        memberJoinDTO.setUNo(uNo);
        log.info("member modify: " + memberJoinDTO);
        memberService.modifyMember(memberJoinDTO);
        return Map.of("result", "modified");
    }
}
