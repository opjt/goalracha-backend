package com.goalracha.service;

import com.goalracha.dto.ReserveListDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ReserveService {
    List<ReserveListDTO> getList();
}
