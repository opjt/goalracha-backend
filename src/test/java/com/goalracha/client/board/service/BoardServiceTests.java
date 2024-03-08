package com.goalracha.client.board.service;

import com.goalracha.dto.board.BoardDTO;
import com.goalracha.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@Log4j2
public class BoardServiceTests {
    @Autowired
    private BoardService boardService;

    // 입력 테스트
    @Test
    public void  BoardRegister() {
        BoardDTO boardDTO = BoardDTO.builder()
                .title("제목")
                .content("내용")
                .createdate(new Date())
                .editdate(new Date())
                .state(1L)  // 0 또는 1 중 하나의 값을 넣어야 합니다.
                .uno(1L)    // 실제 사용자 일련번호를 넣어야 합니다.
                .build();

        Long bno = boardService.register(boardDTO);
        log.info("게시판 일련번호 : " + bno);
    }

    // 조회 테스트
    @Test
    public void BoardGet() {
        Long bno = 1L;
        BoardDTO boardDTO = boardService.get(bno);
        log.info(boardDTO);
    }


    // 목록 테스트
    /* 예제 @Test
    public void BoardList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(1).build();
        PageResponseDTO<BoardDTO> response = boardService.list(pageRequestDTO);
        log.info(response);
    } */

}
