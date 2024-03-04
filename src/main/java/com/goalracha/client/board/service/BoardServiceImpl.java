package com.goalracha.client.board.service;

import com.goalracha.client.board.domain.Board;
import com.goalracha.client.board.dto.BoardDTO;
import com.goalracha.client.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor // 생성자 자동 주입
public class BoardServiceImpl implements BoardService {

    // 자동주입 대상은 final로
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    // 등록
    @Override
    public Long register(BoardDTO boardDTO) {
        log.info("-------------");

        Board board = modelMapper.map(boardDTO, Board.class);
        Board savedBoard = boardRepository.save(board);

        return savedBoard.getBno();
    }

    // 조회
    @Override
    public BoardDTO get(Long bno) {
        return null;
    }

    // 수정
    @Override
    public void modify(BoardDTO boardDTO) {

    }

    // 삭제
    @Override
    public void remove(Long bno) {

    }

}
