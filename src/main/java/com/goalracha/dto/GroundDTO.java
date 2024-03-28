package com.goalracha.dto;

import com.goalracha.entity.Ground;
import com.goalracha.entity.GroundImage;
import com.goalracha.entity.Member;
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

    private Long gNo; // 경기장 번호
    private String name; // 이름
    private String addr; // 주소
    private String inAndOut; // 출입 가능 여부
    private String width; // 폭
    private String grassInfo; // 잔디 정보
    private String recommdMan; // 추천 맨
    private Integer usageTime; // 이용 시간
    private Integer openTime; // 오픈 시간
    private Integer closeTime; // 마감 시간
    private Long fare; // 요금
    private String userGuide; // 사용자 안내
    private String userRules; // 사용 규칙
    private String refundRules; // 환불 규정
    private boolean vestIsYn; // 조끼 여부
    private boolean footwearIsYn; // 신발 여부
    private boolean showerIsYn; // 샤워 여부
    private boolean ballIsYn; // 공 여부 (사용되지 않음)
    private boolean airconIsYn; // 에어컨 여부
    private boolean parkareaIsYn; // 주차장 여부
    private boolean roopIsYn; // 지붕 여부
    private Long state; // 상태
    private Long uNo; // 사용자 번호

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>(); // 파일 목록 (기본값: 빈 ArrayList)
    @Builder.Default
    private List<String> uploadFileNames = new ArrayList<>(); // 업로드된 파일명 목록 (기본값: 빈 ArrayList)

    // Ground 엔티티를 GroundDTO로 변환하는 메서드
    static public GroundDTO entityToDTO(Ground ground) {
        GroundDTO groundDTO = GroundDTO.builder()
                .gNo(ground.getGNo())
                .name(ground.getName())
                .addr(ground.getAddr())
                .inAndOut(ground.getInAndOut())
                .width(ground.getWidth())
                .grassInfo(ground.getGrassInfo())
                .recommdMan(ground.getRecommdMan())
                .usageTime(ground.getUsageTime())
                .openTime(ground.getOpenTime())
                .closeTime(ground.getCloseTime())
                .fare(ground.getFare())
                .userGuide(ground.getUserGuide())
                .userRules(ground.getUserRules())
                .refundRules(ground.getRefundRules())
                .vestIsYn(ground.isVestIsYn())
                .footwearIsYn(ground.isFootwearIsYn())
                .showerIsYn(ground.isShowerIsYn())
                .roopIsYn(ground.isRoopIsYn())
                .airconIsYn(ground.isAirconIsYn())
                .parkareaIsYn(ground.isParkareaIsYn())
                .state(ground.getState())
                .uNo(ground.getMember().getUNo())
                .build();

        // 경기장 이미지 목록이 없거나 비어있는 경우
        List<GroundImage> imageList = ground.getImageList();
        if (imageList == null || imageList.size() == 0) {
            return groundDTO;
        }

        // 파일명 목록을 생성하여 DTO에 설정
        List<String> fileNameList = imageList.stream().map(productImage ->
                productImage.getFileDirectory()).toList();
        groundDTO.setUploadFileNames(fileNameList);
        return groundDTO;
    }

    // GroundDTO를 Ground 엔티티로 변환하는 메서드
    static public Ground dtoToEntity(GroundDTO groundDTO, Member member) {
        Ground ground = Ground.builder()
                .gNo(groundDTO.getGNo())
                .name(groundDTO.getName())
                .addr(groundDTO.getAddr())
                .inAndOut(groundDTO.getInAndOut())
                .width(groundDTO.getWidth())
                .grassInfo(groundDTO.getGrassInfo())
                .recommdMan(groundDTO.getRecommdMan())
                .usageTime(groundDTO.getUsageTime())
                .openTime(groundDTO.getOpenTime())
                .closeTime(groundDTO.getCloseTime())
                .fare(groundDTO.getFare())
                .userGuide(groundDTO.getUserGuide())
                .userRules(groundDTO.getUserRules())
                .refundRules(groundDTO.getRefundRules())
                .vestIsYn(groundDTO.isVestIsYn())
                .footwearIsYn(groundDTO.isFootwearIsYn())
                .showerIsYn(groundDTO.isShowerIsYn())
                .roopIsYn(groundDTO.isRoopIsYn())
                .airconIsYn(groundDTO.isAirconIsYn())
                .parkareaIsYn(groundDTO.isParkareaIsYn())
                .state(groundDTO.getState())
                .member(member)
                .build();

        // 업로드된 파일명이 없는 경우
        List<String> uploadFileNames = groundDTO.getUploadFileNames();
        if (uploadFileNames == null) {
            return ground;
        }

        // 각 파일명을 경기장에 등록
        uploadFileNames.stream().forEach(uploadName -> {
            ground.registerImageName(uploadName);
        });
        return ground;
    }
}
