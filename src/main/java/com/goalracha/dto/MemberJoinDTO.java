package com.goalracha.dto;

import lombok.Data;

@Data
public class MemberJoinDTO {
    private Long uNo;
    private String email;
    private String name;
    private String tel;
    private String nickname;

}
