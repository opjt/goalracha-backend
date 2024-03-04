package com.goalracha.client.board.service;

import com.goalracha.client.board.dto.BoardDTO;

public interface BoardService {
    Long register(BoardDTO boardDTO); // 등록기능
    BoardDTO get(Long bno); // 조회기능
    void modify(BoardDTO boardDTO); // 수정기능
    void remove(Long bno); // 삭제기능

}
