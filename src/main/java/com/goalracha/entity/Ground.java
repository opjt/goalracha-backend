package com.goalracha.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private Long gno;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String addr;

    @Column(name = "inAndOut", nullable = false, length = 50)
    private String inAndOut;

    @Column(nullable = false, length = 50)
    private String width;

    @Column(name = "grassInfo", nullable = false, length = 50)
    private String grassInfo;

    @Column(nullable = false, length = 50)
    private String recommdMan;

    @Column(name = "usagetime", nullable = false, length = 2)
    private Long usageTime;

    @Column(name = "opentime", nullable = false, length = 50)
    private String openTime;

    @Column(name = "closetime", nullable = false, length = 50)
    private String closeTime;

    @Column(nullable = false)
    private Long fare;

    @Column(nullable = false, length = 4000)
    private String userGuide;

    @Column(nullable = false, length = 4000)
    private String userRules;

    @Column(nullable = false, length = 4000)
    private String refundRules;

    @Column(nullable = false, length = 4000)
    private String changeRules;

    @Column(name = "vest_isYN", nullable = false)
    private boolean vestIsYn;

    @Column(name = "footwear_isYn", nullable = false)
    private boolean footwearIsYn;

    @Column(name = "shower_isYn", nullable = false)
    private boolean showerIsYn;

    @Column(name = "ball_isYn", nullable = false)
    private boolean ballIsYn;

    @Column(name = "aircon_isYn", nullable = false)
    private boolean airconIsYn;

    @Column(name = "parkarea_isYn", nullable = false)
    private boolean parkareaIsYn;

    @Column(name = "roop_isYn", nullable = false)
    private boolean roopIsYn;

    @Column(nullable = false)
    private Long state;

    //@ManyToOne
    //@JoinColumn(name = "u_no")
    @Column(name = "u_no", nullable = false)
    private Long uno;

    public void changeName(String name) {
        this.name = name;
    }
    public void changeAddr(String addr) {
        this.addr = addr;
    }
    public void changeInAndOut(String inAndOut){
        this.inAndOut = inAndOut;
    }
    public void changeWidth(String width){
        this.width = width;
    }
    public void changeGrassInfo(String grassInfo){
        this.grassInfo = grassInfo;
    }
    public void changeRecommdMan(String recommdMan){
        this.recommdMan = recommdMan;
    }
    public void changeUsageTime(Long usageTime){
        this.usageTime = usageTime;
    }
    public void changeOpentime(String openTime){
        this.openTime = openTime;
    }
    public void changeCloseTime(String closeTime){
        this.closeTime = closeTime;
    }
    public void changeFare(Long fare) {
        this.fare = fare;
    }
    public void changeUserGuide(String userGuide){
        this.userGuide = userGuide;
    }
    public void changeUserRules(String userRules){
        this.userRules = userRules;
    }
    public void changeRefundRules(String refundRules){
        this.refundRules = refundRules;
    }
    public void changeChangeRules(String changeRules){
        this.changeRules = changeRules;
    }
    public void changeVestIsYn(boolean vestIsYn){
        this.vestIsYn = vestIsYn;
    }
    public void changeFootwearIsYn(boolean vestIsYn){
        this.footwearIsYn = footwearIsYn;
    }
    public void changeShowerIsYn(boolean showerIsYn){
        this.showerIsYn = showerIsYn;
    }
    public void changeBallIsYn(boolean ballIsYn){
        this.ballIsYn = ballIsYn;
    }
    public void changeAirconIsYn(boolean airconIsYn){
        this.airconIsYn = airconIsYn;
    }
    public void changeParkareaIsYn(boolean parkareaIsYn){
        this.parkareaIsYn = parkareaIsYn;
    }
    public void changeRoopIsYn(boolean roopIsYn){
        this.roopIsYn = roopIsYn;
    }
    public void changeState(Long state){
        this.state = state;
    }


}
