package com.goalracha.dto;

import com.goalracha.entity.Member;
import com.goalracha.entity.MemberRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class MemberDTO extends User {

    private Long uNo; // 유저 일련번호
    private String userId; // 유저 아이디
    private String pw; // 비밀번호
    private String nickname; // 닉네임
    private String name; //유저 이름
    private String tel; // 전화번호
    private Date createDate; // 등록 날짜
    private String email; // 이메일
    private String businessName; // 사업자명
    private String businessId; // 사업자 번호
    private Integer state; // 유저 상태 (정상회원, 탈퇴회원)
    private MemberRole type; // 유저 타입 (개인회원, 사업자 회원, 관리자)
    public MemberDTO(Long uNo, String userId, String pw, String nickname, String name, String tel, Date createDate, String email, String businessName,
                     String businessId, Integer state, MemberRole type) {
        super(userId, pw, Collections.singleton(new SimpleGrantedAuthority("ROLE_" + type)));
        this.uNo = uNo;
        this.userId = userId;
        this.pw = pw;
        this.nickname = nickname;
        this.name = name;
        this.tel = tel;
        this.createDate = createDate;
        this.email = email;
        this.businessName = businessName;
        this.businessId = businessId;
        this.state = state;
        this.type = type;
    }

    public MemberDTO(Long uNo, String userId, String pw, MemberRole type) {
        super(userId, pw, Collections.singleton(new SimpleGrantedAuthority("ROLE_" + type)));
        this.uNo = uNo;
        this.userId = userId;
        this.pw = pw;
        this.type = type;
    }
    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("uNo", uNo);
        dataMap.put("userId", userId);
        dataMap.put("pw", pw);
        dataMap.put("nickname", nickname);
        dataMap.put("name", name);
        dataMap.put("tel", tel);
        dataMap.put("createDate", createDate);
        dataMap.put("email", email);
        dataMap.put("businessName", businessName);
        dataMap.put("businessId", businessId);
        dataMap.put("state", state);
        dataMap.put("type", type);
        return dataMap;
    }

    public static MemberDTO fromClaims(Map<String, Object> claims) {
//        Long uNo = (Long) claims.get("uNo");
        Long uNo = Long.parseLong(String.valueOf(claims.get("uNo"))); //claims.get("uNo")가 Integer로 반환해서 타입변환
        String userId = (String) claims.get("userId");
        String pw = (String) claims.get("pw");
        String nickname = (String) claims.get("nickname");
        String name = (String) claims.get("name");
        String tel = (String) claims.get("tel");
//        Date createDate = (Date) claims.get("createDate");
        Date createDate = new Date();
        String email = (String) claims.get("email");
        String businessName = (String) claims.get("businessName");
        String businessId = (String) claims.get("businessId");
        Integer state = (Integer) claims.get("state");
//        MemberRole type = (MemberRole) claims.get("type");
        String typeStr = (String) claims.get("type");
        MemberRole type = MemberRole.valueOf(typeStr); //claims.get("type")이 string으로 반환되서 타입 변환

        return new MemberDTO(uNo, userId, pw, nickname,name, tel, createDate, email, businessName, businessId, state, type);
    }

    public static MemberDTO entityToDTO(Member member) {

        MemberDTO dto = new MemberDTO(member.getUNo(), member.getUserId(), member.getPw(),member.getNickname(),
                member.getName(),member.getTel(),member.getCreateDate(),member.getEmail(),member.getBusinessName(),
                member.getBusinessId(),member.getState(),member.getType());

        return dto;

    }
}