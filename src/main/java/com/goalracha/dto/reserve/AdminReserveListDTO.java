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
public class AdminReserveListDTO {

    private String groundName;
    private String addr;
    private String businessId;
    private String businessName;
    private Date reserveDate;
    private Integer time;
    private Date createDate;
    private Long price;
    private String userName;
    private String email;
}