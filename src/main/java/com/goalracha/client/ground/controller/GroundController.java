package com.goalracha.client.ground.controller;


import com.goalracha.client.ground.dto.GroundDTO;
import com.goalracha.client.ground.dto.PageRequestDTO;
import com.goalracha.client.ground.dto.PageResponseDTO;
import com.goalracha.client.ground.service.GroundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/goalracha/ground")
public class GroundController {
    private final GroundService service;

    @GetMapping("/{gNo}")
    public GroundDTO get(@PathVariable(name = "gNo") Long gNo) {
        return service.get(gNo);
    }

    @GetMapping("/List")
    public PageResponseDTO<GroundDTO> list(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        return service.list(pageRequestDTO);
    }

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody GroundDTO groundDTO) {
        log.info("GroundDTD:" + groundDTO);
        Long gNo = service.register(groundDTO);

        return Map.of("GNO", gNo);
    }

    @PutMapping("/{gNo}")
    public Map<String, String> modify(@PathVariable(name = "gNo") Long gNo, @RequestBody GroundDTO groundDTO) {
        groundDTO.setGNo(gNo);
        log.info("Modify: " + groundDTO);
        service.modify(groundDTO);

        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{gNo}")
    public Map<String, String> remove(@PathVariable(name="gNo") Long gNo) {
        log.info("Remove: " + gNo);
        service.remove(gNo);

        return Map.of("RESULT", "SUCCESS");
    }

}

