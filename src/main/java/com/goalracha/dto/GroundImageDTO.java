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

    private String fileDirectory;
    private int ord;

}

