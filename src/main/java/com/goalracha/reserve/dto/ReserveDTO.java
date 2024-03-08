package com.goalracha.reserve.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDTO {

    private Long rNo;   // 예약 일련번호 PK
    private String reservationTime;    // 예약 시간

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate reservationDate;  // 예약 날짜
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createDate;    // 예약 생성일

    private Integer state;  // 예약 상태 (예약 완료, 예약 취소)
    private Integer paymentType;    // 결제 유형(카카오페이, 무통장 입금, 카드 결제)
    private Long groundId;   // 구장 일련번호 FK
    private Long memberId;   // 유저 일련번호 FK
}