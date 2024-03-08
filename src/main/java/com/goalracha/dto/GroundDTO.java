package com.goalracha.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroundDTO {

    private Long gNo;
    private String name;
    private String addr;
    private String inAndOut;
    private String width;
    private String grassInfo;
    private String recommdMan;
    private Integer usageTime;
    private Integer openTime;
    private Integer closeTime;
    private Long fare;
    private String userGuide;
    private String userRules;
    private String refundRules;
    private String changeRules;
    private boolean vestIsYn;
    private boolean footwearIsYn;
    private boolean showerIsYn;
    private boolean ballIsYn;
    private boolean airconIsYn;
    private boolean parkareaIsYn;
    private boolean roopIsYn;
    private Long state;
    private Long uno;

}
