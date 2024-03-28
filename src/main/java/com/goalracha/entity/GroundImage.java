package com.goalracha.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroundImage {

    private String fileDirectory; // 파일이 저장된 디렉토리 경로
    private int ord; // 이미지의 순서

    // 순서 설정 메서드
    public void setOrd(int ord) {
        this.ord = ord;
    }

}
