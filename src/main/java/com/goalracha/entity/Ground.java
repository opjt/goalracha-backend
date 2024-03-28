package com.goalracha.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Block;

import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(name = "ground_seq_gen", // 시퀀스 제너레이터 이름
        sequenceName = "ground_seq",  // 시퀀스 이름
        initialValue = 1,   // 시작값
        allocationSize = 1) // 메모리를 통해 할당할 범위 사이즈
@Table(name = "ground")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ground {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ground_seq_gen")
    @Column(name = "g_no", nullable = false)
    private Long gNo; // 경기장 번호

    @Column(nullable = false)
    private String name; // 이름

    @Column(nullable = false)
    private String addr; // 주소

    @Column(name = "inAndOut", nullable = false, length = 50)
    private String inAndOut; // 출입 가능 여부

    @Column(nullable = false, length = 50)
    private String width; // 폭

    @Column(name = "grassInfo", nullable = false, length = 50)
    private String grassInfo; // 잔디 정보

    @Column(nullable = false, length = 50)
    private String recommdMan; // 추천 맨

    @Column(name = "usagetime", nullable = false, length = 2)
    private Integer usageTime; // 이용 시간

    @Column(name = "opentime", nullable = false, length = 50)
    private Integer openTime; // 오픈 시간

    @Column(name = "closetime", nullable = false, length = 50)
    private Integer closeTime; // 마감 시간

    @Column(nullable = false)
    private Long fare; // 요금

    @Column(nullable = false, length = 4000)
    private String userGuide; // 사용자 안내

    @Column(nullable = false, length = 4000)
    private String userRules; // 사용 규칙

    @Column(nullable = false, length = 4000)
    private String refundRules; // 환불 규정

    @Column(name = "vest_isYN", nullable = false)
    private boolean vestIsYn; // 조끼 여부

    @Column(name = "footwear_isYn", nullable = false)
    private boolean footwearIsYn; // 신발 여부

    @Column(name = "shower_isYn", nullable = false)
    private boolean showerIsYn; // 샤워 여부

    @Column(name = "ball_isYn", nullable = false)
    private boolean ballIsYn; // 공 여부 (사용되지 않음)

    @Column(name = "aircon_isYn", nullable = false)
    private boolean airconIsYn; // 에어컨 여부

    @Column(name = "parkarea_isYn", nullable = false)
    private boolean parkareaIsYn; // 주차장 여부

    @Column(name = "roop_isYn", nullable = false)
    private boolean roopIsYn; // 지붕 여부

    @Column(nullable = true)
    private Long state; // 상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="u_no", referencedColumnName="u_no")
    private Member member; // 회원

    // 회원을 엔티티로 변환
    public void convertToEntity(Member member) {
        this.member = member;
    }

    // 이름 변경 메서드
    public void changeName(String name) {
        this.name = name;
    }

    // 주소 변경 메서드
    public void changeAddr(String addr) {
        this.addr = addr;
    }

    // 출입 가능 여부 변경 메서드
    public void changeInAndOut(String inAndOut){
        this.inAndOut = inAndOut;
    }

    // 폭 변경 메서드
    public void changeWidth(String width){
        this.width = width;
    }

    // 잔디 정보 변경 메서드
    public void changeGrassInfo(String grassInfo){
        this.grassInfo = grassInfo;
    }

    // 추천 맨 변경 메서드
    public void changeRecommdMan(String recommdMan){
        this.recommdMan = recommdMan;
    }

    // 이용 시간 변경 메서드
    public void changeUsageTime(Integer usageTime){
        this.usageTime = usageTime;
    }

    // 오픈 시간 변경 메서드
    public void changeOpentime(Integer openTime){
        this.openTime = openTime;
    }

    // 마감 시간 변경 메서드
    public void changeCloseTime(Integer closeTime){
        this.closeTime = closeTime;
    }

    // 요금 변경 메서드
    public void changeFare(Long fare) {
        this.fare = fare;
    }

    // 사용자 안내 변경 메서드
    public void changeUserGuide(String userGuide){
        this.userGuide = userGuide;
    }

    // 사용 규칙 변경 메서드
    public void changeUserRules(String userRules){
        this.userRules = userRules;
    }

    // 환불 규정 변경 메서드
    public void changeRefundRules(String refundRules){
        this.refundRules = refundRules;
    }

    // 조끼 여부 변경 메서드
    public void changeVestIsYn(boolean vestIsYn){
        this.vestIsYn = vestIsYn;
    }

    // 신발 여부 변경 메서드
    public void changeFootwearIsYn(boolean vestIsYn){
        this.footwearIsYn = footwearIsYn;
    }

    // 샤워 여부 변경 메서드
    public void changeShowerIsYn(boolean showerIsYn){
        this.showerIsYn = showerIsYn;
    }

    // 공 여부 변경 메서드
    public void changeBallIsYn(boolean ballIsYn){
        this.ballIsYn = ballIsYn;
    }

    // 에어컨 여부 변경 메서드
    public void changeAirconIsYn(boolean airconIsYn){
        this.airconIsYn = airconIsYn;
    }

    // 주차장 여부 변경 메서드
    public void changeParkareaIsYn(boolean parkareaIsYn){
        this.parkareaIsYn = parkareaIsYn;
    }

    // 지붕 여부 변경 메서드
    public void changeRoopIsYn(boolean roopIsYn){
        this.roopIsYn = roopIsYn;
    }

    // 상태 변경 메서드
    public void changeState(Long state){
        this.state = state;
    }


    @ElementCollection
    @Builder.Default
    private List<GroundImage> imageList = new ArrayList<>(); // 경기장 이미지 목록

    // 이미지 등록 메서드
    public void registerImage(GroundImage groundImage) {
        groundImage.setOrd(this.imageList.size());
        imageList.add(groundImage);
    }

    // 파일명으로 이미지 등록 메서드
    public void registerImageName(String fileName) {
        GroundImage groundImage = GroundImage.builder().fileDirectory(fileName).build();
        registerImage(groundImage);
    }

    // 이미지 목록 초기화 메서드
    public void clearList() {
        this.imageList.clear();
    }

}
