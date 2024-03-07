package com.goalracha.controller;

import com.goalracha.dto.ReserveListDTO;
import com.goalracha.service.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/reserv")
public class ReserveController {
    private final ReserveService reservService;

    @GetMapping("/")
    public ResponseEntity<?> getList(){
        List<ReserveListDTO> list = reservService.getList();
        return ResponseEntity.ok("list");
    }

}
