package com.goalracha.dto.reserve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ReserveAddsDTO {

    private Date date; //예약날짜
    private String time; //예약시간 ex)13
    private Long uNo; //유저번호
    private Long gNo; //구장번호

}