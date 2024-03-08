package com.goalracha.dto.reserve;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReserveListDTO {
    private List<?> reserveList;
    private Long groundId;

}
