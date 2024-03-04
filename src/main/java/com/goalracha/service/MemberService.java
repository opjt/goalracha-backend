package com.goalracha.service;

import com.goalracha.dto.MemberDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberService {
    MemberDTO getKakaoMember(String accessToken);


}
