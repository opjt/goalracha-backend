package com.goalracha.dto.reserve;

import com.goalracha.entity.Ground;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ReserveInfoDTO {
    private String groundName; //구장명
    private Date date; //예약날짜
    private String time; //예약시간 ex)13~15

    private Date createDate; //결제날짜
    private Long pay; //결제금액
    private String payType; //결제방식

}
