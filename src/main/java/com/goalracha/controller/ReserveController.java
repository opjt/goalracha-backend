package com.goalracha.controller;

import com.goalracha.dto.reserve.ReservDTO;
import com.goalracha.dto.reserve.ReserveListDTO;
import com.goalracha.entity.Reserve;
import com.goalracha.repository.GroundRepository;
import com.goalracha.repository.ReserveRepository;
import com.goalracha.service.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/reserve")
public class ReserveController {
    private final ReserveService reservService;
    private final ReserveRepository reserveRepository;
    private final GroundRepository groundRepository;

    //예약 전체목록(테스트)
    @GetMapping("/v/")
    public ResponseEntity<?> getList(){
        List<ReserveListDTO> list = reservService.getList();
        return ResponseEntity.ok("list");
    }

    @PostMapping("/v/date/")
    public ResponseEntity<?> getListondate(@RequestBody Map<String, Object> request){

        Map<String, Object> response = reservService.getAllList((String)request.get("date"), (String)request.get("time"), (List<String>) request.get("inout"), (String)request.get("search"));

        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/v/ground/{gno}/{date}")
    public ResponseEntity<?> showGroundInfo(@PathVariable Long gno, @PathVariable String date){
        log.info(date);
        Map<String, Object> response = reservService.showGroundInfo(gno, date);

        return ResponseEntity.ok().body(response);
    }

    //예약등록
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

    //예약번호로 그정보 가져오기
    @GetMapping("/v/{rno}")
    public ResponseEntity<?> getOne(@PathVariable Long rno) {
        Reserve reserve = reservService.getOne(rno);
        log.info(rno);
        return ResponseEntity.ok(reserve.getRNO());
    }

    //구장번호로 예약목록 가져오기
    @GetMapping("/v/ground/{gno}")
    public ResponseEntity<?> getGroundReserve(@PathVariable Long gno) {

        ReserveListDTO dto = reservService.getGroundReserve(gno);

        return ResponseEntity.ok(dto);
    }

}
