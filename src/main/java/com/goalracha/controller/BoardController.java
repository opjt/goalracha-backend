package com.goalracha.controller;

import com.goalracha.entity.Board;
import com.goalracha.dto.board.BoardDTO;
import com.goalracha.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService service;



    @GetMapping("/g/{bno}")
    public BoardDTO get(@PathVariable(name = "bno") Long bno) {
        return service.get(bno);
    }

    // 스카이홍 리스트
    @GetMapping("/g/list")
    public List<BoardDTO> boardList(Board board) {
        // someParam을 사용하여 필요한 로직 수행
        return service.boardList(board);
    }

    // 포스트맨
    // 입력
    @PostMapping("/")
    public Map<String, Long> register(@RequestBody BoardDTO boardDTO) {
        log.info("BoardDTO : " + boardDTO);
        Long bno = service.register(boardDTO);

        return Map.of("BNO", bno);
    }

    // 수정
    @PutMapping("/{bno}")
    public Map<String, String> modify(@PathVariable(name = "bno") Long bno,
                                      @RequestBody BoardDTO boardDTO) {
        boardDTO.setBno(bno);
        log.info("Modify : " + boardDTO);
        service.modify(boardDTO);

        return Map.of("RESULT", "SUCCESS");
    }

    // 삭제
    @DeleteMapping("/{bno}")
    public Map<String, String> remove(@PathVariable(name = "bno") Long bno) {
        log.info("Remove : " + bno);
        service.remove(bno);

        return Map.of("RESILT", "SUCCESS");
    }

}
