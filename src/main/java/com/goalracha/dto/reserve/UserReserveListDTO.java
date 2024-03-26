package com.goalracha.dto.reserve;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReserveListDTO {
    private String payKey;
    private String groundName;
    private String groundAddr;
    private Date reserveDate;
    private String time;
    private Date createDate;
    private Long price;
    private Integer state;
    private Integer usageTime;
    private String userName;
    private String userEmail;
    private String userTel;
//    private String payType;


}