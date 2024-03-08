package com.goalracha.repository;

import com.goalracha.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
//    @Query("select m from Member m where m.id = :id")
//    Member findByUserid(@Param("id") String id);

    //Member findByUserId(String id); //유저이이디로 유저 찾기
    Member findByUserId(String userId); //유저이이디로 유저 찾기

    Member findByEmail(String email); //유저이메일로 유저 찾기
}
