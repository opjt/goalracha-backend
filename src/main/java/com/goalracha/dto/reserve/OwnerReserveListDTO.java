package com.goalracha.dto.reserve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnerReserveListDTO {

    private String groundName;
    private String addr;
    private Date reserveDate;
    private Integer time;
    private Date createDate;
    private Long price;
    private Integer state;
    private String userName;
    private String email;
    private String tel;
    private Integer usageTime;
}