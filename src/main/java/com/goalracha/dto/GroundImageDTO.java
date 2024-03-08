package com.goalracha.dto;

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
public class GroundImageDTO {
    private Long p_no;
    private String pname;
    private Long g_no;

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();
    @Builder.Default
    private List<String> uploadFileNames = new ArrayList<>();
}
