package com.goalracha.service;

import com.goalracha.dto.MemberDTO;
import com.goalracha.dto.MemberJoinDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberService {
    MemberDTO getKakaoMember(String accessToken);


    void modifyMember(MemberJoinDTO memberJoinDTO);
}
