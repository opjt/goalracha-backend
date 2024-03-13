package com.goalracha.service;

import com.goalracha.dto.*;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MemberService {
    MemberDTO getKakaoMember(String accessToken);


    void modifyMember(MemberJoinDTO memberJoinDTO);

    boolean checkId(String userid);

    Long ownerJoin(OwnerJoinDTO joinDto);

    void userModify(MemberModifyDTO memberModifyDTO);

    void ownerPwModify(OwnerPwModifyDTO ownerPwModifyDTO);

    void ownerNameModify(OwnerNameModifyDTO ownerNameModifyDTO);
}
