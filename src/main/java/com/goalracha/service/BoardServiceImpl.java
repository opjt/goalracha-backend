package com.goalracha.service;

import com.goalracha.entity.Board;
import com.goalracha.dto.board.BoardDTO;
import com.goalracha.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor // 생성자 자동 주입
public class BoardServiceImpl implements BoardService {

    // 자동주입 대상은 final로
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;

    @Override
    public List<BoardDTO> boardList(Board board) {

        // Repository 또는 EntityManager를 사용하여 데이터베이스에서 엔티티를 조회합니다.
        List<Board> boardEntities = boardRepository.findAll(); // 또는 entityManager.createQuery(...).getResultList();

        // DTO로 변환하여 반환합니다.
        return boardEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BoardDTO convertToDTO(Board board) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBno(board.getBno());
        boardDTO.setTitle(board.getTitle());
        boardDTO.setContent(board.getContent());
        // 필요한 다른 필드들을 복사합니다.
        return boardDTO;
    }

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
        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();
        BoardDTO dto = modelMapper.map(board, BoardDTO.class);


        return dto;
    }

    // 수정
    @Override
    public void modify(BoardDTO boardDTO) {
        Optional<Board> result = boardRepository.findById(boardDTO.getBno());

        Board board = result.orElseThrow();

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());
        board.changeEditdate(boardDTO.getEditdate());

        boardRepository.save(board);
    }

    // 삭제
    @Override
    public void remove(Long bno) {
        boardRepository.deleteById(bno);
    }

    // 목록구현
   /* public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                 pageRequestDTO.getPage() - 1, // 1페이지가 0이므로 주의
                            pageRequestDTO.getSize(), Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageable);
        List<BoardDTO> dtoList = result.getContent().stream().map(board ->
                modelMapper.map(board, BoardDTO.class)).collect(Collectors.toList());
        long totalCount = result.getTotalElements();
        PageResponseDTO<BoardDTO> responseDTO = PageResponseDTO.<BoardDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();

        return responseDTO;
    }*/

}
