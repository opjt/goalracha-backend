package com.goalracha.service;

import com.goalracha.dto.ReserveListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReserveServiceImpl implements ReserveService{
    @Override
    public List<ReserveListDTO> getList() {

        return null;
    }
}
