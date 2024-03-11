package com.goalracha.service;

import com.goalracha.dto.GroundDTO;
import com.goalracha.dto.reserve.ReservDTO;
import com.goalracha.dto.reserve.ReserveListDTO;
import com.goalracha.entity.Ground;
import com.goalracha.entity.Member;
import com.goalracha.entity.Reserve;
import com.goalracha.repository.GroundRepository;
import com.goalracha.repository.MemberRepository;
import com.goalracha.repository.ReserveRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

    @Override //날짜,시간,실내외로 예약가능 전체 구장목록 출력하기
    public Map<String, Object> getAllList(String reqDate, String reqTime, String reqInout) {
        Map<String, Object> result = new HashMap<>();
        List<Object[]> list = reserveRepository.findGroundsWithReservationsOnDate("2024-03-11");
        List<GroundDTO> groundList= groundRepository.findAllGroundsWithoutMember();
        Map<Long, GroundDTO> groundMap = groundList.stream()
                .collect(Collectors.toMap(GroundDTO::getGNo, Function.identity()));

        List<String> reqTimeList = Arrays.asList(reqTime.split(","));
        int timeCount = reqTimeList.size();
        result.put("reserv", list);
        result.put("groundlist", groundMap);

        String jpql = "SELECT g.gNo, LISTAGG(TO_Char(r.time), ',') WITHIN GROUP (ORDER BY r.rNO) AS gg " +
                "FROM Ground g " +
                "LEFT OUTER JOIN Reserve r ON g.gNo = r.ground.gNo AND FUNCTION('to_char', r.reserveDate, 'yyyy-mm-dd') = :date " +
                "WHERE g.state != 0 " +
                "AND g.gNo NOT IN (" +
                "    SELECT r2.ground.gNo " +
                "    FROM Reserve r2" +
                "    WHERE r2.time IN ("+reqTime +") " +
                "    GROUP BY r2.ground.gNo " +
                "    HAVING COUNT(DISTINCT time) =  " + timeCount+
                ") " +
                "AND g.inAndOut IN ("+reqInout+") " +
                "GROUP BY g.gNo";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("date", reqDate);
        List<Object[]> result2 = query.getResultList();
        result.put("result2" , result2);

        List<Object[]> reesult = new ArrayList<>();
        for (Object[] row : result2) {
            Long gNo = (Long) row[0];
            if(row[1] == null) {
                reesult.add(new Object[]{gNo, null});
                continue;
            }
            String times = (String) row[1];
            List<String> timesplit = Arrays.asList(times.split(","));
            if(times.equals(reqTime)) { //시간이 똑같으면 넘어감
                continue;
            }

            int i = 0;
            for(String sf1: reqTimeList) {
                log.info("timesplit:" + sf1 + "contain : " + reqTimeList.contains(sf1) + "check : " + checkReserveTime(groundMap.get(gNo).getOpenTime(),groundMap.get(gNo).getCloseTime(),Integer.parseInt(sf1),
                        groundMap.get(gNo).getUsageTime()) );
                if(checkReserveTime(groundMap.get(gNo).getOpenTime(),groundMap.get(gNo).getCloseTime(),Integer.parseInt(sf1),
                        groundMap.get(gNo).getUsageTime()) && !timesplit.contains(sf1)) {
                    i++;
                    break;
                }
            }
            if(i > 0) {
                reesult.add(new Object[]{gNo, times});
            }
        }
        result.put("result3", reesult);


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

    @Override
    public ReserveListDTO getGroundReserve(Long gno) {
        List<ReservDTO> result = reserveRepository.listGroundReserveDTO(gno);
        log.info("result :: " + result);
        ReserveListDTO listdto = ReserveListDTO.builder().reserveList(result).groundId(gno).build();
        log.info("listdto :: " + listdto);

        return listdto;
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
