package com.goalracha.reserve.service;

import com.goalracha.domain.Ground;
import com.goalracha.domain.Member;
import com.goalracha.domain.Reserve;
import com.goalracha.repository.GroundRepository;
import com.goalracha.repository.MemberRepository;
import com.goalracha.repository.ReserveRepository;
import com.goalracha.reserve.dto.PageRequestDTO;
import com.goalracha.reserve.dto.PageResponseDTO;
import com.goalracha.reserve.dto.ReserveDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor    // 생성자 자동 주입
public class ReserveServiceImpl implements ReserveService {

    // 자동 주입 대상은 final
    private final ModelMapper modelMapper;
    private final ReserveRepository reserveRepository;
    private final GroundRepository groundRepository; // Ground 엔티티를 다루는 Repository
    private final MemberRepository memberRepository; // Member 엔티티를 다루는 Repository


    @Override
    public Long register(ReserveDTO reserveDTO) {

        // ReserveDTO로부터 구장 ID와 회원 ID를 가져옵니다.
        Long groundId = reserveDTO.getGroundId();
        Long memberId = reserveDTO.getMemberId();
        log.info(groundId + " " + memberId);
        // 구장 엔티티 조회
        Ground ground = groundRepository.findById(groundId)
                .orElseThrow(() -> new IllegalArgumentException("구장을 찾을 수 없습니다. ID: " + groundId)); // 수정된 부분

        // 회원 엔티티 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. ID: " + memberId)); // 수정된 부분

        // Reserve 엔티티를 생성합니다.
        Reserve reserve = Reserve.builder()
                .reservationTime(reserveDTO.getReservationTime())  // 예약 시간 설정
                .reservationDate(reserveDTO.getReservationDate())  // 예약 날짜 설정
                .createDate(reserveDTO.getCreateDate())  // 생성 날짜 설정
                .state(reserveDTO.getState())  // 상태 설정
                .paymentType(reserveDTO.getPaymentType())  // 결제 유형 설정
                .groundId(ground)  // 구장 설정
                .memberId(member)  // 회원 설정
                .build();

        // 로그 정보 출력
        log.info(reserve.toString());

        // 예약 저장
        Reserve savedReserve = reserveRepository.save(reserve);

        // 저장된 예약의 번호를 반환합니다.
        return savedReserve.getRNo();
    }

    @Override
    public ReserveDTO get(Long rNo) {

        java.util.Optional<Reserve> result = reserveRepository.findById(rNo);

        Reserve reserve = result.orElseThrow();
        ReserveDTO rto = modelMapper.map(reserve, ReserveDTO.class);

        return rto;
    }

    @Override
    public void remove(Long rNo) {

        reserveRepository.deleteById(rNo);
    }

    @Override
    public PageResponseDTO<ReserveDTO> list(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1, // 1 페이지 0
                pageRequestDTO.getSize(), Sort.by("rNo").descending());

        Page<Reserve> result = reserveRepository.findAll(pageable);

        List<ReserveDTO> dtoList = result.getContent().stream().map(reserve -> modelMapper.
                map(reserve, ReserveDTO.class)).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        PageResponseDTO<ReserveDTO> responseDTO = PageResponseDTO.<ReserveDTO>withAll().
                dtoList(dtoList).pageRequestDTO(pageRequestDTO).totalCount(totalCount).build();

        return responseDTO;
    }

}
