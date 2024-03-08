package com.goalracha.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reserve")
@SequenceGenerator(name = "reserve_seq_gen", // 시퀀스 제너레이터 이름
        sequenceName = "reserve_seq",  // 시퀀스 이름
        initialValue = 1,   // 시작값
        allocationSize = 1) // 메모리를 통해 할당할 범위 사이즈
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Setter
@Builder
public class Reserve {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserve_seq_gen")
    @Column(name = "r_no")
    private Long rNo;   // 예약 일련번호 PK

    @Column(name = "reservation_timp")
    private String reservationTime;    // 예약 시간

    @Column(name = "reservation_date")
    private LocalDate reservationDate;  // 예약 날짜

    @Column(name = "create_date")
    private LocalDate createDate;    // 예약 생성일

    @Column(name = "state")
    private Integer state;  // 예약 상태 (예약 완료, 예약 취소)

    @Column(name = "payment_type")
    private Integer paymentType;    // 결제 유형(카카오페이, 무통장 입금, 카드 결제)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "g_no")
    private Ground groundId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_no")
    private Member memberId;


}
