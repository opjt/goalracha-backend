package com.goalracha.dto.reserve;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReserveListDTO {

    private String name;
    private String addr;
    private Date reserveDate;
    private Integer time;
    private Date createDate;
    private Long price;

}