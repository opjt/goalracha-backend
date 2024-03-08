package com.goalracha.controller;

import com.goalracha.dto.reserve.ReservDTO;
import com.goalracha.dto.reserve.ReserveListDTO;
import com.goalracha.entity.Reserve;
import com.goalracha.service.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/reserve")
public class ReserveController {
    private final ReserveService reservService;

    @GetMapping("/v/")
    public ResponseEntity<?> getList(){
        List<ReserveListDTO> list = reservService.getList();
        return ResponseEntity.ok("list");
    }

    @PostMapping("/v/")
    public ResponseEntity<?> newReserve(@RequestBody ReservDTO reservDTO) {
        log.info("reserveDTO :: " + reservDTO);

        Reserve reserve = reservService.newReserve(reservDTO);
        if(reserve == null) {
            return ResponseEntity.badRequest().body("error");
        }
        log.info(reserve.getRNO());
        return ResponseEntity.ok(reserve.getRNO());
    }

    @GetMapping("/v/{gno}")
    public ResponseEntity<?> getOne(@PathVariable Long rno) {
        Reserve reserve = reservService.getOne(rno);
        log.info(rno);
        return ResponseEntity.ok(reserve.getRNO());
    }
    @GetMapping("/v/ground/{gno}")
    public ResponseEntity<?> getGroundReserve(@PathVariable Long gno) {

        ReserveListDTO dto = reservService.getGroundReserve(gno);

        return ResponseEntity.ok(dto);
    }

}
