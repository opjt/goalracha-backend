package com.goalracha.service;

import com.goalracha.dto.MemberDTO;
import com.goalracha.dto.MemberJoinDTO;
import com.goalracha.dto.OwnerJoinDTO;
import com.goalracha.entity.MemberRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MemberService {
    MemberDTO getKakaoMember(String accessToken);


    void modifyMember(MemberJoinDTO memberJoinDTO);

    boolean checkId(String userid);

    Long ownerJoin(OwnerJoinDTO joinDto);

    List<MemberDTO> findMembersByType(MemberRole type); // 타입으로 멤버들 조회

}
