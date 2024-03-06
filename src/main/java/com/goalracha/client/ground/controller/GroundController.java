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

    @GetMapping("/{gno}")
    public GroundDTO get(@PathVariable(name = "gno") Long gno) {
        return service.get(gno);
    }

    @GetMapping("/List")
    public PageResponseDTO<GroundDTO> list(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        return service.list(pageRequestDTO);
    }

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody GroundDTO groundDTO) {
        log.info("GroundDTD:" + groundDTO);
        Long gno = service.register(groundDTO);

        return Map.of("GNO", gno);
    }

    @PutMapping("/{gno}")
    public Map<String, String> modify(@PathVariable(name = "gno") Long gno, @RequestBody GroundDTO groundDTO) {
        groundDTO.setGno(gno);
        log.info("Modify: " + groundDTO);
        service.modify(groundDTO);

        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{gno}")
    public Map<String, String> remove(@PathVariable(name="gno") Long gno) {
        log.info("Remove: " + gno);
        service.remove(gno);

        return Map.of("RESULT", "SUCCESS");
    }

}

