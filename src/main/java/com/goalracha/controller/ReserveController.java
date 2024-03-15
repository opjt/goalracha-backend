package com.goalracha.controller;

import com.goalracha.dto.reserve.ReservDTO;
import com.goalracha.dto.reserve.ReserveAddsDTO;
import com.goalracha.dto.reserve.ReserveListDTO;
import com.goalracha.dto.reserve.UserReserveListDTO;
import com.goalracha.entity.Reserve;
import com.goalracha.repository.GroundRepository;
import com.goalracha.repository.ReserveRepository;
import com.goalracha.service.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/reserve")
public class ReserveController {

    private final ReserveService reserveService;
    private final ReserveRepository reserveRepository;
    private final GroundRepository groundRepository;

    //예약 전체목록(테스트)
    @GetMapping("/v/")
    public ResponseEntity<?> getList() {
        List<ReserveListDTO> list = reserveService.getList();
        return ResponseEntity.ok("list");
    }

    @PostMapping("/v/date/")
    public ResponseEntity<?> getListondate(@RequestBody Map<String, Object> request) {

        Map<String, Object> response = reserveService.getAllList((String) request.get("date"), (String) request.get("time"), (List<String>) request.get("inout"), (String) request.get("search"));

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/v/ground/{gno}/{date}")
    public ResponseEntity<?> showGroundInfo(@PathVariable Long gno, @PathVariable String date) {
        log.info(date);
        Map<String, Object> response = reserveService.showGroundInfo(gno, date);

        return ResponseEntity.ok().body(response);
    }

    //예약등록
    @PostMapping("/v/")
    public ResponseEntity<?> newReserve(@RequestBody ReservDTO reservDTO) {
        log.info("reserveDTO :: " + reservDTO);

        Reserve reserve = reserveService.newReserve(reservDTO);
        if (reserve == null) {
            return ResponseEntity.badRequest().body("error");
        }
        log.info(reserve.getRNO());
        return ResponseEntity.ok(reserve.getRNO());
    }
    @PostMapping("/v/p/")
    public ResponseEntity<?> addReserve(@RequestBody ReserveAddsDTO reserveAddsdto) {

        Map<String, Object> response = reservService.newReserve(reserveAddsdto.getGNo(),reserveAddsdto.getUNo(),reserveAddsdto.getDate(),reserveAddsdto.getTime());
        if(response == null) {
            return ResponseEntity.badRequest().body("error");
        }
        return ResponseEntity.ok(response);
    }

    //예약번호로 그정보 가져오기
    @GetMapping("/v/{rno}")
    public ResponseEntity<?> getOne(@PathVariable Long rno) {
        Reserve reserve = reserveService.getOne(rno);
        log.info(rno);
        return ResponseEntity.ok(reserve.getRNO());
    }

    //구장번호로 예약목록 가져오기
    @GetMapping("/v/ground/{gno}")
    public ResponseEntity<?> getGroundReserve(@PathVariable Long gno) {

        ReserveListDTO dto = reserveService.getGroundReserve(gno);

        return ResponseEntity.ok(dto);
    }


    // 유저 번호로 예약목록 가져오기
    @GetMapping("/v/list/{uNo}")
    public ResponseEntity<List<UserReserveListDTO>> getUserReservationList(@PathVariable Long uNo) {
        List<UserReserveListDTO> userReservations = reserveService.getUserReserve(uNo);
        return ResponseEntity.ok(userReservations);
    }

}
