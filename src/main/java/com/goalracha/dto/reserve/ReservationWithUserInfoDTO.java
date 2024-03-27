package com.goalracha.dto.reserve;

import com.goalracha.entity.Member;
import com.goalracha.entity.Reserve;
import lombok.Data;
import java.util.Date;

@Data // Lombok을 사용하여 게터, 세터, toString 등을 자동 생성\
public class ReservationWithUserInfoDTO {
    // 필드 선언 예시
    private Long reserveId;
    private Date reserveDate;
    private String groundName;
    private Integer time;        // 예약 시간
    private Long price;       // 예약 가격

    private Long userId;
    private String userName;
    private String userEmail;

    // 생성자
    public ReservationWithUserInfoDTO(Reserve reserve, Member member) {
        this.reserveId = reserve.getRNO(); // 예약 ID 초기화
        this.reserveDate = reserve.getReserveDate(); // 예약 날짜 초기화
        this.groundName = reserve.getGround().getName(); // 구장 이름 초기화
        this.time = reserve.getTime(); // 예약 시간 초기화
        this.price = reserve.getPrice(); // 예약 가격 초기화

        this.userId = member.getUNo(); // 사용자 ID 초기화
        this.userName = member.getName(); // 사용자 이름 초기화
        this.userEmail = member.getEmail(); // 사용자 이메일 초기화
    }
    // 게터와 세터는 생략
}