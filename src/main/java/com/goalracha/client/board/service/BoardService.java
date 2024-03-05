package com.goalracha.client.board.service;

import com.goalracha.client.board.domain.Board;
import com.goalracha.client.board.dto.BoardDTO;

import java.util.List;

public interface BoardService {

    Long register(BoardDTO boardDTO); // 등록기능
    BoardDTO get(Long bno); // 조회기능
    void modify(BoardDTO boardDTO); // 수정기능
    void remove(Long bno); // 삭제기능

    List<BoardDTO> boardList(Board board);
    //PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO); // 페이지, 리스트기능




}
