package com.goalracha.reserve.controller;

import com.goalracha.reserve.dto.PageRequestDTO;
import com.goalracha.reserve.dto.PageResponseDTO;
import com.goalracha.reserve.dto.ReserveDTO;
import com.goalracha.reserve.service.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/reserve")
public class ReserveController {

    private final ReserveService service;

    @GetMapping("/{rNo}")
    public ReserveDTO get(@PathVariable(name = "rNo") Long rNo) {

        return service.get(rNo);
    }

    @GetMapping("/list")
    public PageResponseDTO<ReserveDTO> list(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);
        return service.list(pageRequestDTO);
    }

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody ReserveDTO reserveDTO) {
        log.info("ReserveDTO : " + reserveDTO);
        Long rNo = service.register(reserveDTO);
        return Map.of("RNO", rNo);
    }

    @DeleteMapping("/{rNo}")
    public Map<String, String> remove(@PathVariable(name = "rNo") Long rNo){
        log.info("Remove : " + rNo);
        service.remove(rNo);

        return Map.of("RESULT", "SUCCESS");
    }

}
