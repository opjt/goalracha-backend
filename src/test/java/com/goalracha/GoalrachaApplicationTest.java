package com.goalracha;

import com.goalracha.client.board.domain.Board;
import com.goalracha.client.board.repository.BoardRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@Log4j2
@SpringBootTest
public class GoalrachaApplicationTest {
    @Autowired
    private BoardRepository boardRepository;

    // 테스트 시작
    @Test
    public void test() {
        log.info("----------------");
        log.info(boardRepository);
    }

    // 추가
    @Test
    public void BoardInsert() {
        for (int i = 1; i <= 10; i++) {
            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .createdate(new Date())
                    .editdate(new Date())
                    .state(1L)
                    .uno(1L)
                    .build();

            Board savedBoard = boardRepository.save(board);
            log.info("Saved Board: {}", savedBoard);
        }
    }

    // 조회
    @Test
    public void BoardRead() {
        Long bno = 2L;
        java.util.Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        log.info(board);
    }

    // 수정
    @Test
    public void BoardModify() {
        Long bno = 2L;
        java.util.Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        board.changeTitle("제목"); // 제목수정
        board.changeContent("내용"); // 내용수정
        board.changeEditdate(new Date()); //수정일시

        boardRepository.save(board);
    }

    // 삭제
    @Test
    public void BoardDelete() {
        Long bno = 1L;

        boardRepository.deleteById(bno);
    }
}

