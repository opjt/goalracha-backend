package com.goalracha.service;

import com.goalracha.dto.GroundDTO;
import com.goalracha.dto.PageRequestDTO;
import com.goalracha.dto.PageResponseDTO;
import com.goalracha.dto.reserve.*;
import com.goalracha.entity.Ground;
import com.goalracha.entity.Member;
import com.goalracha.entity.Reserve;
import com.goalracha.repository.GroundRepository;
import com.goalracha.repository.MemberRepository;
import com.goalracha.repository.ReserveRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ReserveServiceImpl implements ReserveService {
    private final ReserveRepository reserveRepository;
    private final MemberRepository memberRepository;
    private final GroundRepository groundRepository;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    @Override //날짜,시간,실내외로 예약가능 전체 구장목록 출력하기
    public Map<String, Object> getAllList(String reqDate, String reqTime, List<String> reqInout, String search) {
        Map<String, Object> result = new HashMap<>(); //최종리턴맵

        List<Ground> groundList2 = groundRepository.findAll(); //수정필요
        List<GroundDTO> dtoList = groundList2.stream().map(GroundDTO::entityToDTO).toList();
//        List<GroundDTO> groundList= groundRepository.findAllGroundsWithoutMember();
        Map<Long, GroundDTO> groundMap = dtoList.stream()
                .collect(Collectors.toMap(GroundDTO::getGNo, Function.identity()));

        List<String> reqTimeList = Arrays.asList(reqTime.split(","));
        int timeCount = reqTimeList.size();

        result.put("groundlist", groundMap);

        String jpql = "SELECT g.gNo, LISTAGG(TO_Char(r.time), ',') WITHIN GROUP (ORDER BY r.time) AS gg " +
                "FROM Ground g " +
                "LEFT OUTER JOIN Reserve r ON g.gNo = r.ground.gNo AND FUNCTION('to_char', r.reserveDate, 'yyyy-mm-dd') = :date and r.state != 0 " +
                "WHERE g.state != 0 " +
                "AND g.gNo NOT IN (" +
                "    SELECT r2.ground.gNo " +
                "    FROM Reserve r2" +
                "    WHERE r2.time IN (" + reqTime + ") " +
                "    GROUP BY r2.ground.gNo " +
                "    HAVING COUNT(DISTINCT time) =  " + timeCount +
                ") " +
                "AND g.inAndOut IN :inout ";
        if (search != null) {
            jpql += "AND (g.addr LIKE :searchParam OR g.name LIKE :searchParam) ";
            // searchParam에 %1%를 포함하는 문자열을 설정
        }
        jpql += "GROUP BY g.gNo";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("date", reqDate);
        query.setParameter("inout", reqInout);
        if (search != null) {
            query.setParameter("searchParam", "%" + search + "%"); // searchParam 변수에 값을 설정
        }
        List<Object[]> result2 = query.getResultList();
        result.put("result2", result2);
//        List<Long> gnoArrayList = new ArrayList<>(); //구장의번호목록
//        for (Object[] objArray : result2) {
//            gnoArrayList.add((Long)objArray[0]);
//        }
//        result.put("gnostring", gnoArrayList);
        List<Object[]> reservList = new ArrayList<>();
        for (Object[] row : result2) {
            Long gNo = (Long) row[0];
            String times;

            if (row[1] == null) { //예약이 없으면 물음표값넣어서 for문돌릴때 무조건 포함안되어있지만 올바른 시간인지는 확인
                times = "?,?";
            } else {
                times = (String) row[1];
            }
            if (times.equals(reqTime)) { //시간이 똑같으면 넘어감
                continue;
            }
            List<String> timesplit = Arrays.asList(times.split(","));
            int i = 0;
            for (String sf1 : reqTimeList) { //시간필터의 값들로 올바른 예약시간인지 확인
//                log.info("timesplit:" + sf1 + "contain : " + timesplit.contains(sf1) + "check : " + checkReserveTime(groundMap.get(gNo).getOpenTime(), groundMap.get(gNo).getCloseTime(), Integer.parseInt(sf1),
//                        groundMap.get(gNo).getUsageTime()));
                if (checkReserveTime(groundMap.get(gNo).getOpenTime(), groundMap.get(gNo).getCloseTime(), Integer.parseInt(sf1),
                        groundMap.get(gNo).getUsageTime()) && !timesplit.contains(sf1)) {
                    i++;
                    break;
                }
            }
            if (i > 0) { //한개라도 있으면 추가
                reservList.add(new Object[]{gNo, (String) row[1]});
            }
        }
        result.put("groundreservList", reservList);

        return result;


    }

    @Override //그라운드 아이디랑 날짜로 구장상세페이지의 필요한 정보 리턴
    public Map<String, Object> showGroundInfo(Long gno, String date) {

        Map<String, Object> result = new HashMap<>(); //최종리턴맵
        Ground groundE = groundRepository.findById(gno).orElse(null);
        if (groundE == null) {
            return null;
        }
        GroundDTO grounddto = GroundDTO.entityToDTO(groundE);
        result.put("groundInfo", grounddto);
        log.info(date);
        List<ReservDTO> reservList = reserveRepository.listGroundReserveDTO(gno, date);
        result.put("reservList", reservList);
        return result;
    }

    @Override
    public List<ReserveListDTO> getList() {

        return null;
    }

    @Override
    public Reserve getOne(Long gno) {
        Reserve result = reserveRepository.findById(gno).orElse(null);
        return result;
    }

    // user 이전 예약
    @Override
    public PageResponseDTO<UserReserveListDTO> getUserPreviousReservations(Long uNo, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1, // 1페이지가 0이므로 주의
                pageRequestDTO.getSize(),
                Sort.by("rNO").descending()
        );

        // 사용자의 이전 예약을 가져옵니다.
        Page<UserReserveListDTO> page = reserveRepository.userPreviousReservations(uNo, pageable);

        // 가져온 이전 예약을 PageResponseDTO 형식으로 변환하여 반환합니다.
        return PageResponseDTO.<UserReserveListDTO>withAll()
                .dtoList(page.getContent())
                .pageRequestDTO(PageRequestDTO.builder()
                        .page(page.getNumber() + 1)
                        .size(page.getSize())
                        .build())
                .totalCount(page.getTotalElements())
                .build();
    }

    // user 예약 현황
    @Override
    public PageResponseDTO<UserReserveListDTO> getUserReservationStatus(Long uNo, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1, // 1페이지가 0이므로 주의
                pageRequestDTO.getSize(),
                Sort.by("rNO").descending()
        );
        // 사용자의 예약 현황을 가져옵니다.
        Page<UserReserveListDTO> page = reserveRepository.userReservationStatus(uNo, pageable);

        // 가져온 예약 현황을 PageResponseDTO 형식으로 변환하여 반환합니다.
        return PageResponseDTO.<UserReserveListDTO>withAll()
                .dtoList(page.getContent())
                .pageRequestDTO(PageRequestDTO.builder()
                        .page(page.getNumber() + 1)
                        .size(page.getSize())
                        .build())
                .totalCount(page.getTotalElements())
                .build();
    }

    // owner 예약 목록
    @Override // owner 예약 리스트
    public PageResponseDTO<OwnerReserveListDTO> getOwnerReserveList(Long uNo, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1, // 1페이지가 0이므로 주의
                pageRequestDTO.getSize(),
                Sort.by("rNO").descending()
        );
        // 해당 사용자의 예약 리스트를 가져옵니다.
        Page<OwnerReserveListDTO> page = reserveRepository.ownerReserveList(uNo, pageable);

        // 가져온 예약 리스트를 PageResponseDTO 형식으로 변환하여 반환합니다.
        return PageResponseDTO.<OwnerReserveListDTO>withAll()
                .dtoList(page.getContent())
                .pageRequestDTO(PageRequestDTO.builder()
                        .page(page.getNumber() + 1)
                        .size(page.getSize())
                        .build())
                .totalCount(page.getTotalElements())
                .build();
    }

    // admin 전체 예약 목록
    @Override // 예약 전체 리스트(관리자)
    public PageResponseDTO<AdminReserveListDTO> getAllReserveList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1, // 1페이지가 0이므로 주의
                pageRequestDTO.getSize(),
                Sort.by("rNO").descending()
        );

        // 모든 예약 리스트를 가져옵니다.
        Page<AdminReserveListDTO> page = reserveRepository.findAllReserveList(pageable);
        log.info(page.toString());

        // 가져온 예약 리스트를 PageResponseDTO 형식으로 변환하여 반환합니다.
        return PageResponseDTO.<AdminReserveListDTO>withAll()
                .dtoList(page.getContent())
                .pageRequestDTO(PageRequestDTO.builder()
                        .page(page.getNumber() + 1)
                        .size(page.getSize())
                        .build())
                .totalCount(page.getTotalElements())
                .build();
    }

    @Override
    public Map<String, Object> cancel(String header, String payKey, Long uNo) {
        Map<String,Object> result = new HashMap<>();

        List<Reserve> reservList = reserveRepository.findAllByPayKey(payKey);

        if(reservList == null) { //잘못된 paykey일경우
            log.error("pay키 찾을 수 없음");
            return null;
        }
        if(reservList.get(0).getMember().getUNo() != uNo) {
            log.error(reservList.get(0).getMember().getUNo() + " " + uNo + "다름");
            return null;
        }
        Map<String,Object> tossResult = tossPostCancel(header,payKey);

        if(tossResult == null) {
            return null;
        }
        for (Reserve reserve : reservList) {
            reserve.delete();
            reserveRepository.save(reserve);
        }
        result.put("res", "good");
        return result;

    }
    // 사업자 예약 리스트 검색 (구장명, 고객명)
    @Override
    public PageResponseDTO<OwnerReserveListDTO> getOwnerReserveListSearch(Long uNo, String searchName, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1, // 1페이지가 0이므로 주의
                pageRequestDTO.getSize(),
                Sort.by("rNO").descending()
        );

        // 예약 리스트 가져오기
        Page<OwnerReserveListDTO> page = reserveRepository.ownerRserveListSearch(uNo, searchName, pageable);

        // 가져온 예약 리스트를 PageResponseDTO 형식으로 변환하여 반환합니다.
        return PageResponseDTO.<OwnerReserveListDTO>withAll()
                .dtoList(page.getContent())
                .pageRequestDTO(PageRequestDTO.builder()
                        .page(page.getNumber() + 1)
                        .size(page.getSize())
                        .build())
                .totalCount(page.getTotalElements())
                .build();

    }

    // 사업자 예약 리스트 검색 (구장명, 고객명)
    @Override
    public PageResponseDTO<AdminReserveListDTO> getAllReserveListSearch(String searchName, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1, // 1페이지가 0이므로 주의
                pageRequestDTO.getSize(),
                Sort.by("rNO").descending()
        );

        // 예약 리스트 가져오기
        Page<AdminReserveListDTO> page = reserveRepository.findAllReserveListSearch(searchName, pageable);

        // 가져온 예약 리스트를 PageResponseDTO 형식으로 변환하여 반환합니다.
        return PageResponseDTO.<AdminReserveListDTO>withAll()
                .dtoList(page.getContent())
                .pageRequestDTO(PageRequestDTO.builder()
                        .page(page.getNumber() + 1)
                        .size(page.getSize())
                        .build())
                .totalCount(page.getTotalElements())
                .build();

    }

    @Override
    public Map<String, Object> infoByPayKey(String payKey) {
        Map<String,Object> result = new HashMap<>(); //최종결과맵

        List<Reserve> reservList = reserveRepository.findAllByPayKey(payKey); //payKey가 동일한 예약목록 불러오기
        if(reservList.isEmpty()) { //값이 없으면
            result.put("error","payKey 찾을 수 없음");
            return result;
        }
        String timeList = reservList.stream()
                .map(reserve -> String.valueOf(reserve.getTime()))
                .collect(Collectors.joining(","));

        Reserve firstReserve = reservList.get(0);
//        if(reservList.size() >= 2) {
//            timeList = reservList.get(0).getTime() + ":00 ~ " + reservList.get(reservList.size()-1).getTime()+firstReserve.getGround().getUsageTime() + ":00";
//        } else {
//            timeList = firstReserve.getTime() + ":00 ~ " + firstReserve.getTime()+firstReserve.getGround().getUsageTime() +  ":00";
//        }

        ReserveInfoDTO reserveResult = ReserveInfoDTO.builder()
                .groundName(firstReserve.getGround().getName())
                .date(firstReserve.getReserveDate())
                .time(timeList)
                .createDate(firstReserve.getCreateDate())
                .pay(firstReserve.getPrice())
                .payType(firstReserve.getPayType())
                .build();
        result.put("reserveInfo", reserveResult);

        GroundDTO ground = GroundDTO.entityToDTO(firstReserve.getGround());
        result.put("groundInfo", ground);
        return result;
    }


    @Override
    public ReserveListDTO getGroundReserve(Long gno) {
        List<ReservDTO> result = reserveRepository.listGroundReserveDTO(gno, "");
        log.info("result :: " + result);
        ReserveListDTO listdto = ReserveListDTO.builder().reserveList(result).groundId(gno).build();
        log.info("listdto :: " + listdto);

        return listdto;
    }

    @Override
    public Map<String, Object> newReserve(ReserveAddsDTO requestDTO) {

        Long gNo = requestDTO.getGNo();
        Long uNo = requestDTO.getUNo();
        Date reqDate = requestDTO.getDate();
        String time = requestDTO.getTime();

        Map<String, Object> result = new HashMap<>(); //최종리턴맵

        Member member = memberRepository.findById(uNo).orElse(null);
        Ground ground = groundRepository.findById(gNo).orElse(null);
        List<Integer> resTimeList = new ArrayList<>();
        if (member == null || ground == null) { //구장이나 맴버정보가 맞지 않으면 리턴널
            return null;
        }
        result.put("ground", GroundDTO.entityToDTO(ground));
        result.put("memberName", member.getName());
        List<Integer> timeList = Arrays.stream(time.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        for (int oftime : timeList) {
            if (!checkReserveTime(ground.getOpenTime(), ground.getCloseTime(), oftime, ground.getUsageTime())) { //예약가능 시간대인지 확인
                log.error("불가능한 시간 " + ground.getOpenTime() + " " + ground.getCloseTime() + " " + oftime + " " + ground.getUsageTime());
                return null;
            }

            List<Integer> getTimeList = reserveRepository.findReservationTimesByDate(ground.getGNo(), reqDate); //중복시간 있는지 확인
            if (getTimeList.contains(oftime)) {
                log.error("중복시간 검출" + oftime + " " + getTimeList.toString());
                return null;
            }

            Reserve newreserv = Reserve.builder()
                    .reserveDate(reqDate)
                    .time(oftime)
                    .payType(requestDTO.getPayType())
                    .payKey(requestDTO.getPayKey())
                    .createDate(new Date())
                    .member(member)
                    .ground(ground)
                    .price(ground.getFare())
                    .state(1)
                    .build();
            Reserve reserve = reserveRepository.save(newreserv);
            resTimeList.add(reserve.getTime());
        }
        result.put("resTimeList", resTimeList);
        result.put("date", reqDate);


        return result;
    }

    private boolean checkReserveTime(Integer openTime, Integer closeTime, Integer useTime, Integer unit) {
        //가능시간대인지 확인하는 메소드
        if (closeTime <= openTime) {
            closeTime += 24;
        }
        if (!(openTime <= useTime && useTime <= closeTime)) {
            return false;
        } else {

            if (unit == 0) { //1시간 단위면 모든시간 다 가능
                return true;
            }
            if (useTime + unit > closeTime) {
                return false; //이용시간이 마감시간을 초과하는 경우
            }
            if ((useTime - openTime) % unit == 0) { //시간단위를 벗어나는 경우
                return true;
            }

        }
        return false;
    }

    private Map<String, Object> tossPostCancel(String header, String payKey) {

        String tossCancelUrl = "https://api.tosspayments.com/v1/payments/" + payKey + "/cancel";

        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + header);
        headers.add("Content-Type", "application/json");

        // HTTP 요청 본문 설정
        HttpEntity<String> entity = new HttpEntity<>("{\"cancelReason\":\"고객이 취소를 원함\"}", headers);

        try {
            // HTTP POST 요청 보내기
            ResponseEntity<Map> response = restTemplate.exchange(tossCancelUrl, HttpMethod.POST, entity, Map.class);

            // 응답 처리
            HttpStatusCode statusCode = response.getStatusCode();
            Map<String, Object> responseBody = response.getBody();
            System.out.println("Status code: " + statusCode);
            System.out.println("Response body: " + responseBody);
            return responseBody;

        } catch (HttpClientErrorException e) {
            // 클라이언트 에러 (예: 400 Bad Request) 처리
            HttpStatusCode statusCode = e.getStatusCode();
            String responseBody = e.getResponseBodyAsString();
            System.err.println("Client error occurred. Status code: " + statusCode);
            System.err.println("Response body: " + responseBody);
            // 예외를 더 처리하거나 반환할 수 있습니다.
            return null;
        }
    }
    //    @Override //예약 추가 최초버전
//    public Reserve newReserve(ReservDTO reservDTO) {
//        Member member = memberRepository.findById(reservDTO.getUNo()).orElse(null);
//        Ground ground = groundRepository.findById(reservDTO.getGNo()).orElse(null);
//        if (member == null || ground == null) { //구장이나 맴버정보가 맞지 않으면 리턴널
//            return null;
//        }
//        if (!checkReserveTime(ground.getOpenTime(), ground.getCloseTime(), reservDTO.getTime(), ground.getUsageTime())) { //예약가능 시간대인지 확인
//            log.error("불가능한 시간 " + ground.getOpenTime() + " " + ground.getCloseTime() + " " + reservDTO.getTime() + " " + ground.getUsageTime());
//            return null;
//        }
//        List<Integer> timeList = reserveRepository.findReservationTimesByDate(ground.getGNo(), reservDTO.getReserveDate()); //중복시간 있는지 확인
//        if (timeList.contains(reservDTO.getTime())) {
//            log.error("중복시간 검출" + reservDTO.getTime() + " " + timeList.toString());
//            return null;
//        }
//        Reserve newreserv = Reserve.builder()
//                .reserveDate(reservDTO.getReserveDate())
//                .time(reservDTO.getTime())
//                .payType(reservDTO.getPayType())
//                .createDate(new Date())
//                .member(member)
//                .ground(ground)
//                .state(1)
//                .build();
//        Reserve result = reserveRepository.save(newreserv);
//
//        return result;
//    }
}
