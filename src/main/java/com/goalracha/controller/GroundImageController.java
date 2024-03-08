package com.goalracha.controller;


import com.goalracha.dto.GroundImageDTO;
import com.goalracha.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/image")
public class GroundImageController {
    private final CustomFileUtil fileUtil;

    @PostMapping("/")
    public Map<String, String> register(GroundImageDTO DTO) {
        log.info("register: " + DTO);
        List<MultipartFile> files = DTO.getFiles();
        List<String> uploadFileNames = fileUtil.saveFiles(files);
        DTO.setUploadFileNames(uploadFileNames);
        log.info(uploadFileNames);
        return Map.of("RESULT", "SUCCESS");
    }

    @GetMapping("/view/{fileName}") // 화면에서 upload 폴더에있는 사진 체크
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName) {
        return fileUtil.getFile(fileName);
    }
}
