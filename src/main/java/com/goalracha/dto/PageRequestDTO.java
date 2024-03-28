package com.goalracha.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page = 1; // 페이지 번호. 기본값은 1

    @Builder.Default
    private int size = 10; // 페이지 크기. 한 페이지당 아이템 수. 기본값은 10

}
