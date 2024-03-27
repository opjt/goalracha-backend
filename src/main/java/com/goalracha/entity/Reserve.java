package com.goalracha.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name="reservation")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Reserve {

    @Id
    @SequenceGenerator(name = "reserve_seq_gen", sequenceName = "reserve_seq",initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserve_seq_gen")
    @Column(name = "r_no")
    private Long rNO; //예약 일련번호

    @Column(name = "time")
    private Integer time; //예약 시간

    @Column(name = "state")
    private Integer state; //예약 상태

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(name = "reserve_date")
    private Date reserveDate; //예약한 날짜

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(name = "create_date")
    private Date createDate; //예약 생성날짜

    @Column(name = "payment_type")
    private String payType; //결제방식

    @Column(name = "payment_key")
    private String payKey; //결제키(환불할 때 필요)

    @Column(name = "price")
    private Long price; // 가격

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="g_no")
    private Ground ground;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="u_no")
    private Member member;

    public void delete() {
        this.state = 0;
    }
}
