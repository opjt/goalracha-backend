package com.goalracha.service;

import com.goalracha.dto.MemberDTO;
import com.goalracha.dto.MemberJoinDTO;
import com.goalracha.dto.OwnerJoinDTO;
import com.goalracha.entity.MemberRole;
import com.goalracha.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MemberService {
    MemberDTO getKakaoMember(String accessToken);


    void modifyMember(MemberJoinDTO memberJoinDTO);

    boolean checkId(String userid);

    boolean checkNickname(String nickname); // 닉네임 중복 검사 메서드

    Long ownerJoin(OwnerJoinDTO joinDto);

    List<MemberDTO> findMembersByType(MemberRole type); // 타입으로 멤버들 조회


    void userModify(MemberModifyDTO memberModifyDTO);

    void ownerPwModify(OwnerPwModifyDTO ownerPwModifyDTO);

    void ownerNameModify(OwnerNameModifyDTO ownerNameModifyDTO);

    void withdrawMember(Long uNo); // 회원 탈퇴 메서드

}
