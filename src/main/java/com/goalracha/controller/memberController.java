package com.goalracha.controller;

import com.goalracha.dto.OwnerJoinDTO;
import com.goalracha.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class memberController {
    private final MemberService memberService;

    @PostMapping("/api/member/checkid")
    public Map<String, Object> modify(@RequestBody String userid) {
        boolean check = memberService.checkId(userid);
        return Map.of("result", check);
    }

    @PostMapping("/api/member/owner")
    public Map<String, Object> joinOwner(@RequestBody OwnerJoinDTO joinDto) {

        Long memberId = memberService.ownerJoin(joinDto);
        return Map.of("result", memberId);
    }
}
