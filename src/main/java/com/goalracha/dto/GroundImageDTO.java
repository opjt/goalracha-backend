package com.goalracha.dto;

import com.goalracha.entity.Ground;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroundImageDTO {

    private String fileDirectory; // 파일이 저장된 디렉토리 경로
    private int ord; // 이미지의 순서

}

