package com.goalracha.client.ground.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class PageResponseDTO<E> {

    private List<E> dtoList;
    private List<Integer> pageNumList;
    private PageRequestDTO pageRequestDTO;
    private boolean prev;
    private boolean next;
    private int totalCount;
    private int prevCount;
    private int nextCount;
    private int totalPage;
    private int current;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long totalCount) {
        this.dtoList = dtoList;
        this.pageRequestDTO = pageRequestDTO;
        this.totalCount = (int) totalCount;

        int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;
        int start = end - 9;
        int last = (int) (Math.ceil(totalCount / (double)pageRequestDTO.getSize()));
    }
}
