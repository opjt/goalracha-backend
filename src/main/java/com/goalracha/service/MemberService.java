package com.goalracha.service;

import com.goalracha.dto.MemberDTO;
import com.goalracha.dto.MemberJoinDTO;
import com.goalracha.dto.MemberModifyDTO;
import com.goalracha.dto.OwnerJoinDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberService {
    MemberDTO getKakaoMember(String accessToken);


    void modifyMember(MemberJoinDTO memberJoinDTO);

    boolean checkId(String userid);

    Long ownerJoin(OwnerJoinDTO joinDto);

    void userModify(MemberModifyDTO memberModifyDTO);
}
