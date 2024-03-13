package com.goalracha.controller;

import com.goalracha.dto.MemberDTO;
import com.goalracha.dto.OwnerJoinDTO;
import com.goalracha.entity.Member;
import com.goalracha.entity.MemberRole;
import com.goalracha.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
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
    @GetMapping("/api/member/user")
    public ResponseEntity<List<MemberDTO>> getUserMembers() {
        List<MemberDTO> users = memberService.findMembersByType(MemberRole.USER);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/api/member/owner")
    public ResponseEntity<List<MemberDTO>> getOwnerMembers() {
        List<MemberDTO> owners = memberService.findMembersByType(MemberRole.OWNER);
        return ResponseEntity.ok(owners);
    }


}
