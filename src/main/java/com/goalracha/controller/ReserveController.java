package com.goalracha.controller;

import com.goalracha.dto.PageRequestDTO;
import com.goalracha.dto.PageResponseDTO;
import com.goalracha.dto.reserve.*;
import com.goalracha.entity.Reserve;
import com.goalracha.repository.GroundRepository;
import com.goalracha.repository.ReserveRepository;
import com.goalracha.service.ReserveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        Map<String, Object> response = reserveService.newReserve(reserveAddsdto.getGNo(), reserveAddsdto.getUNo(), reserveAddsdto.getDate(), reserveAddsdto.getTime());
        if (response == null) {
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


    // user 이전 예약 목록 조회
    @GetMapping("/v/previous-reservations/{uNo}")
    public ResponseEntity<PageResponseDTO<UserReserveListDTO>> getUserPreviousReservations(
            @PathVariable Long uNo, @PageableDefault(size = 10) Pageable pageable) {

        // 사용자의 이전 예약을 가져옵니다.
        PageResponseDTO<UserReserveListDTO> responseDTO = reserveService.getUserPreviousReservations(uNo, pageable);

        // ResponseEntity에 담아 반환합니다.
        return ResponseEntity.ok(responseDTO);
    }

    // 유저 번호로 예약현황 목록 조회
    @GetMapping("/v/reservation-status/{uNo}")
    public ResponseEntity<PageResponseDTO<UserReserveListDTO>> getUserReservationStatus(
            @PathVariable Long uNo,
            @PageableDefault(size = 10) Pageable pageable) {
        // 사용자의 예약 현황을 가져옵니다.
        PageResponseDTO<UserReserveListDTO> responseDTO = reserveService.getUserReservationStatus(uNo, pageable);
        // ResponseEntity에 담아 반환합니다.
        return ResponseEntity.ok(responseDTO);
    }

    // 구장 유저 번호로 예약 목록 조회
    @GetMapping("/v/owner-list/{uNo}")
    public ResponseEntity<PageResponseDTO<AdminReserveListDTO>> getOwnerReserveList(
            @PathVariable Long uNo,
            @PageableDefault(size = 10) Pageable pageable) {
        // 해당 사용자의 예약 리스트를 가져옵니다.
        PageResponseDTO<AdminReserveListDTO> responseDTO = reserveService.getOwnerReserveList(uNo, pageable);
        // ResponseEntity에 담아 반환합니다.
        return ResponseEntity.ok(responseDTO);
    }


    // 예약 전체 리스트(관리자)
    @GetMapping("/v/list")
    public ResponseEntity<PageResponseDTO<AdminReserveListDTO>> getAllReserveList(
            @PageableDefault(size = 10) PageRequestDTO pageRequestDTO) {
        // 모든 예약 리스트를 가져옵니다.
        PageResponseDTO<AdminReserveListDTO> responseDTO = reserveService.getAllReserveList(pageRequestDTO);
        // ResponseEntity에 담아 반환합니다.
        return ResponseEntity.ok(responseDTO);
    }


}
