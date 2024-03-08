package com.goalracha.dto.reserve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ReservDTO {
    private Long rNo; //예약일련번호
    private Integer payType; //결제방식
    private Date reserveDate; //예약날짜
    private Date createDate; //예약생성날짜
    private Integer time; //예약시간 ex)13
    private Integer state; //예약상태 / 1이면 표시
    private Long uNo; //유저번호
    private Long gNo; //구장번호


}
