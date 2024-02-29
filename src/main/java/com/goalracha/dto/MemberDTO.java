package com.goalracha.dto;


import com.goalracha.domain.MemberRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class MemberDTO extends User {

    private Long uNo;
    private String userId;
    private String pw;
    private String nickname;
    private String tel;
    private LocalDate createDate;

    private String email;
    private String businessName;
    private String businessId;
    private Integer state;
    private MemberRole type;
    public MemberDTO(Long uNo, String userId, String pw, String nickname,String tel, LocalDate createDate, String email, String businessName,
                     String businessId, Integer state, MemberRole type) {
        super(userId, pw, Collections.singleton(new SimpleGrantedAuthority("ROLE_" + type)));
        this.uNo = uNo;
        this.userId = userId;
        this.pw = pw;
        this.nickname = nickname;
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
        Long uNo = Long.parseLong(String.valueOf(claims.get("uNo"))); //claims.get("uNo")가 Integer로 반환해서 재반환
        String userId = (String) claims.get("userId");
        String pw = (String) claims.get("pw");
        String nickname = (String) claims.get("nickname");
        String tel = (String) claims.get("tel");
        LocalDate createDate = (LocalDate) claims.get("createDate");
        String email = (String) claims.get("email");
        String businessName = (String) claims.get("businessName");
        String businessId = (String) claims.get("businessId");
        Integer state = (Integer) claims.get("state");
//        MemberRole type = (MemberRole) claims.get("type");
        String typeStr = (String) claims.get("type");
        MemberRole type = MemberRole.valueOf(typeStr); //claims.get("type")이 string으로 반환되서 재봔환

        return new MemberDTO(uNo, userId, pw, nickname, tel, createDate, email, businessName, businessId, state, type);
    }
}