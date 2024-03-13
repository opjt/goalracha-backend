package com.goalracha.repository;

import com.goalracha.entity.Member;
import com.goalracha.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
//    @Query("select m from Member m where m.id = :id")
//    Member findByUserid(@Param("id") String id);

    //Member findByUserId(String id); //유저이이디로 유저 찾기
    Member findByUserId(String userId); //유저이이디로 유저 찾기

    Member findByEmail(String email); //유저이메일로 유저 찾기

    List<Member> findByType(MemberRole type); // 타입으로 멤버 찾기
}
