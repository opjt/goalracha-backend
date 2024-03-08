package com.goalracha.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Setter
@Table(name="member")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member {

    @Id
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq",initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @Column(name = "u_no")
    private Long uNo; //유저 일련번호

    @Column(name = "id", unique = true)
    private String userId; //유저 아이디

    @Column(name = "pw")
    private String pw; //유저 비밀번호

    @Column(name = "nickname", unique= true)
    private String nickname;//유저 닉네임

    @Column(name = "name")
    private String name; //유저 이름

    @Column(name = "tel")
    private String tel; //유저 전화번호

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(name = "createdate")
    private Date createDate; //유저 생성날짜

    @Column(name = "email")
    private String email; //이메일

    @Column(name = "business_name")
    private String businessName; //사업자회원 이름

    @Column(name = "business_id")
    private String businessId; //사업자번호

    @Column(name = "state")
    private Integer state; //유저 상태

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MemberRole type; //유저타입 user,owner,admin

    public void joinMember(Long uNo, String name, String nickname, String tel) {
        this.uNo = uNo;
        this.nickname = nickname;
        this.name = name;
        this.tel = tel;

    }
}
