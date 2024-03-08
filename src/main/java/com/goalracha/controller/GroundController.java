package com.goalracha.controller;


import com.goalracha.dto.GroundDTO;
import com.goalracha.dto.PageRequestDTO;
import com.goalracha.dto.PageResponseDTO;
import com.goalracha.service.GroundService;
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

    @GetMapping("/read/{gno}")
    public GroundDTO get(@PathVariable(name = "gno") Long gNo) {
        return service.get(gNo);
    }

    @GetMapping("/")
    public PageResponseDTO<GroundDTO> list(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        return service.list(pageRequestDTO);
    }

    @PostMapping("/register" )
    public Map<String, Long> register(@RequestBody GroundDTO groundDTO) {
        log.info("GroundDTD:" + groundDTO);
        Long gNo = service.register(groundDTO);

        return Map.of("gno", gNo);
    }

    @PutMapping("/modify/{gno}")
    public Map<String, String> modify(@PathVariable(name = "gno") Long gNo, @RequestBody GroundDTO groundDTO) {
        groundDTO.setGNo(gNo);
        log.info("Modify: " + groundDTO);
        service.modify(groundDTO);

        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/delete/{gno}")
    public Map<String, String> delete(@PathVariable(name="gno") Long gNo) {
        log.info("Remove: " + gNo);
        service.delete(gNo);

        return Map.of("RESULT", "SUCCESS");
    }

}

