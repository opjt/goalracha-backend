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
    private Date reserveDate;
    private Integer time;
    private Date createDate;
    private Long price;
    private String addr;
    private String userName;
    private String businessId;
    private String businessName;
    private String email;
}
