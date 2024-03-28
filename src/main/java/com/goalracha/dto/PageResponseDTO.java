package com.goalracha.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<E> {

    private List<E> dtoList; // 페이지에 대한 DTO 목록
    private List<Integer> pageNumList; // 페이지 번호 목록
    private PageRequestDTO pageRequestDTO; // 페이지 요청 DTO
    private boolean prev; // 이전 페이지 존재 여부
    private boolean next; // 다음 페이지 존재 여부
    private int totalCount; // 전체 아이템 수
    private int prevPage; // 이전 페이지 번호
    private int nextPage; // 다음 페이지 번호
    private int totalPage; // 전체 페이지 수
    private int current; // 현재 페이지 번호

    // 모든 필드를 인자로 받는 빌더 메서드
    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalCount) {
        this.dtoList = dtoList; // DTO 목록 설정
        this.pageRequestDTO = pageRequestDTO; // 페이지 요청 DTO 설정
        this.totalCount = (int) totalCount; // 전체 아이템 수 설정

        // 현재 페이지 범위 설정
        int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;
        int start = end - 9;

        int last = (int) (Math.ceil((totalCount / (double) pageRequestDTO.getSize())));
        end = end > last ? last : end;

        // 이전 페이지 및 다음 페이지 설정
        this.prev = start > 1;
        this.next = totalCount > end * pageRequestDTO.getSize();

        // 페이지 번호 목록 생성
        this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

        // 이전 페이지가 존재하는 경우 이전 페이지 번호 설정
        if (prev) {
            this.prevPage = start - 1;
        }
        // 다음 페이지가 존재하는 경우 다음 페이지 번호 설정
        if (next) {
            this.nextPage = end + 1;
        }

        // 전체 페이지 수 설정
        this.totalPage = this.pageNumList.size();
        // 현재 페이지 번호 설정
        this.current = pageRequestDTO.getPage();
    }
}
