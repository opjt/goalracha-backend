package com.goalracha.dto;


import com.goalracha.entity.Ground;
import com.goalracha.entity.GroundImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
    private boolean vestIsYn;
    private boolean footwearIsYn;
    private boolean showerIsYn;
    private boolean ballIsYn;
    private boolean airconIsYn;
    private boolean parkareaIsYn;
    private boolean roopIsYn;
    private Long state;
    private Long uNo;

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();
    @Builder.Default
    private List<String> uploadFileNames = new ArrayList<>();

    static public GroundDTO entityToDTO(Ground ground) {
        GroundDTO groundDTO = GroundDTO.builder().gNo(ground.getGNo()).name(ground.getName()).addr(ground.getAddr())
                .inAndOut(ground.getInAndOut()).width(ground.getWidth()).grassInfo(ground.getGrassInfo())
                .recommdMan(ground.getRecommdMan()).usageTime(ground.getUsageTime()).openTime(ground.getOpenTime())
                .closeTime(ground.getCloseTime()).fare(ground.getFare()).userGuide(ground.getUserGuide())
                .userRules(ground.getUserRules()).refundRules(ground.getRefundRules()).vestIsYn(ground.isVestIsYn())
                .footwearIsYn(ground.isFootwearIsYn()).showerIsYn(ground.isShowerIsYn()).roopIsYn(ground.isRoopIsYn())
                .airconIsYn(ground.isAirconIsYn()).parkareaIsYn(ground.isParkareaIsYn()).build();
        List<GroundImage> imageList = ground.getImageList();
        if (imageList == null || imageList.size() == 0) {

            return groundDTO;
        }

        List<String> fileNameList = imageList.stream().map(productImage ->
                productImage.getFileDirectory()).toList();
        groundDTO.setUploadFileNames(fileNameList);
        return groundDTO;

    }
    static public Ground dtoToEntity(GroundDTO groundDTO) {
        Ground ground = Ground.builder().gNo(groundDTO.getGNo()).name(groundDTO.getName()).addr(groundDTO.getAddr())
                .inAndOut(groundDTO.getInAndOut()).width(groundDTO.getWidth()).grassInfo(groundDTO.getGrassInfo())
                .recommdMan(groundDTO.getRecommdMan()).usageTime(groundDTO.getUsageTime()).openTime(groundDTO.getOpenTime())
                .closeTime(groundDTO.getCloseTime()).fare(groundDTO.getFare()).userGuide(groundDTO.getUserGuide())
                .userRules(groundDTO.getUserRules()).refundRules(groundDTO.getRefundRules()).vestIsYn(groundDTO.isVestIsYn())
                .footwearIsYn(groundDTO.isFootwearIsYn()).showerIsYn(groundDTO.isShowerIsYn()).roopIsYn(groundDTO.isRoopIsYn())
                .airconIsYn(groundDTO.isAirconIsYn()).parkareaIsYn(groundDTO.isParkareaIsYn())
                .build();

        List<String> uploadFileNames = groundDTO.getUploadFileNames();
        if (uploadFileNames == null) {
            return ground;
        }
        uploadFileNames.stream().forEach(uploadName -> {
            ground.registerImageName(uploadName);
        });
        return ground;
    }

}
