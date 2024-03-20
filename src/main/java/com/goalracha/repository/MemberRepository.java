package com.goalracha.repository;

import com.goalracha.entity.Member;
import com.goalracha.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {
//    @Query("select m from Member m where m.id = :id")
//    Member findByUserid(@Param("id") String id);

    //Member findByUserId(String id); //유저이이디로 유저 찾기
    Member findByUserId(String userId); //유저이이디로 유저 찾기

    Member findByEmail(String email); //유저이메일로 유저 찾기

    List<Member> findByType(MemberRole type); // 타입으로 멤버 찾기

    boolean existsByNickname(String nickname); // 닉네임 중복 여부 확인

    Member findByNickname(String nickname); // 닉네임으로 유저 찾기

    // 오늘 이후의 예약 내역이 있는지 확인하기 위한 쿼리를 수행
    @Query("SELECT COUNT(r) FROM Reserve r WHERE r.member.uNo = :uNo AND r.reserveDate >= CURRENT_DATE")
    int countReservationsAfterToday(@Param("uNo") Long uNo);

    // 회원의 상태를 탈퇴로 변경하는 쿼리를 수행
    @Query("UPDATE Member m SET m.state = 2 WHERE m.uNo = :uNo")
    void updateMemberStateToWithdraw(@Param("uNo") Long uNo);

}
