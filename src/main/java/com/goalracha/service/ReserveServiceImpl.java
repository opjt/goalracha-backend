package com.goalracha.service;

import com.goalracha.dto.GroundDTO;
import com.goalracha.dto.reserve.ReservDTO;
import com.goalracha.dto.reserve.ReserveListDTO;
import com.goalracha.dto.reserve.UserReserveListDTO;
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
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ReserveServiceImpl implements ReserveService{
    private final ReserveRepository reserveRepository;
    private final MemberRepository memberRepository;
    private final GroundRepository groundRepository;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    @Override //날짜,시간,실내외로 예약가능 전체 구장목록 출력하기
    public Map<String, Object> getAllList(String reqDate, String reqTime, List<String> reqInout, String search) {
        Map<String, Object> result = new HashMap<>(); //최종리턴맵

        List<Ground> groundList2 = groundRepository.findByState(1L);
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
                "    WHERE r2.time IN ("+reqTime +") " +
                "    GROUP BY r2.ground.gNo " +
                "    HAVING COUNT(DISTINCT time) =  " + timeCount+
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
        result.put("result2" , result2);
//        List<Long> gnoArrayList = new ArrayList<>(); //구장의번호목록
//        for (Object[] objArray : result2) {
//            gnoArrayList.add((Long)objArray[0]);
//        }
//        result.put("gnostring", gnoArrayList);
        List<Object[]> reservList = new ArrayList<>();
        for (Object[] row : result2) {
            Long gNo = (Long) row[0];
            String times;

            if(row[1] == null) { //예약이 없으면 물음표값넣어서 for문돌릴때 무조건 포함안되어있지만 올바른 시간인지는 확인
                times =  "?,?";
            } else {
                times = (String) row[1];
            }
            if(times.equals(reqTime)) { //시간이 똑같으면 넘어감
                continue;
            }
            List<String> timesplit = Arrays.asList(times.split(","));
            int i = 0;
            for(String sf1: reqTimeList) { //시간필터의 값들로 올바른 예약시간인지 확인
                log.info("timesplit:" + sf1 + "contain : " + timesplit.contains(sf1) + "check : " + checkReserveTime(groundMap.get(gNo).getOpenTime(),groundMap.get(gNo).getCloseTime(),Integer.parseInt(sf1),
                        groundMap.get(gNo).getUsageTime()) );
                if(checkReserveTime(groundMap.get(gNo).getOpenTime(),groundMap.get(gNo).getCloseTime(),Integer.parseInt(sf1),
                        groundMap.get(gNo).getUsageTime()) && !timesplit.contains(sf1)) {
                    i++;
                    break;
                }
            }
            if(i > 0) { //한개라도 있으면 추가
                reservList.add(new Object[]{gNo, (String)row[1]});
            }
        }
        result.put("groundreservList", reservList);

        return result;


    }

    @Override //그라운드 아이디랑 날짜로 구장상세페이지의 필요한 정보 리턴
    public Map<String, Object> showGroundInfo(Long gno, String date) {

        Map<String, Object> result = new HashMap<>(); //최종리턴맵
        Ground groundE = groundRepository.findById(gno).orElse(null);
        if(groundE == null ) {
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

    // uNo로 예약목록
    public List<UserReserveListDTO> getUserReserve(Long uNo) {
        return reserveRepository.findReservationsByUserNo(uNo);
    }

    // 회원 탈퇴
    @Override
    public boolean hasReservations(Long uNo) {
        return false;
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
    public Map<String, Object> newReserve(Long gNo, Long uNo, Date reqDate, String time) {
        Map<String, Object> result = new HashMap<>(); //최종리턴맵

        Member member = memberRepository.findById(uNo).orElse(null);
        Ground ground = groundRepository.findById(gNo).orElse(null);
        List<Integer> resTimeList = new ArrayList<>();
        if(member == null || ground == null) { //구장이나 맴버정보가 맞지 않으면 리턴널
            return null;
        }
        result.put("ground", GroundDTO.entityToDTO(ground));
        result.put("memberName", member.getName());
        List<Integer> timeList = Arrays.stream(time.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        for(int oftime : timeList) {
            if(!checkReserveTime(ground.getOpenTime(), ground.getCloseTime(), oftime ,ground.getUsageTime())) { //예약가능 시간대인지 확인
                log.error("불가능한 시간 " + ground.getOpenTime() + " " +  ground.getCloseTime()+ " "+  oftime+ " " +ground.getUsageTime());
                return null;
            }

            List<Integer> getTimeList = reserveRepository.findReservationTimesByDate(ground.getGNo(),reqDate); //중복시간 있는지 확인
            if(getTimeList.contains(oftime)) {
                log.error("중복시간 검출" + oftime + " " + getTimeList.toString());
                return null;
            }

            Reserve newreserv = Reserve.builder()
                    .reserveDate(reqDate)
                    .time(oftime)
                    .payType("1")
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

    @Override
    public Reserve newReserve(ReservDTO reservDTO) {

        Member member = memberRepository.findById(reservDTO.getUNo()).orElse(null);
        Ground ground = groundRepository.findById(reservDTO.getGNo()).orElse(null);

        if(member == null || ground == null) { //구장이나 맴버정보가 맞지 않으면 리턴널
            return null;
        }

        if(!checkReserveTime(ground.getOpenTime(), ground.getCloseTime(), reservDTO.getTime(),ground.getUsageTime())) { //예약가능 시간대인지 확인
            log.error("불가능한 시간 " + ground.getOpenTime() + " " +  ground.getCloseTime()+ " "+  reservDTO.getTime()+ " " +ground.getUsageTime());
            return null;
        }

        List<Integer> timeList = reserveRepository.findReservationTimesByDate(ground.getGNo(),reservDTO.getReserveDate()); //중복시간 있는지 확인
        if(timeList.contains(reservDTO.getTime())) {
            log.error("중복시간 검출" + reservDTO.getTime() + " " + timeList.toString());
            return null;
        }


        Reserve newreserv = Reserve.builder()
                .reserveDate(reservDTO.getReserveDate())
                .time(reservDTO.getTime())
                .payType(reservDTO.getPayType())
                .createDate(new Date())
                .member(member)
                .ground(ground)
                .state(1)
                .build();
        Reserve result = reserveRepository.save(newreserv);

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

            if(unit == 0) { //1시간 단위면 모든시간 다 가능
                return true;
            }
            if(useTime+ unit > closeTime) {
                return false; //이용시간이 마감시간을 초과하는 경우
            }
            if((useTime - openTime) % unit == 0) { //시간단위를 벗어나는 경우
                return true;
            }

        }
        return false;
    }
}
