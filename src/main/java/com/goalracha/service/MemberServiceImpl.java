package com.goalracha.service;

import com.goalracha.dto.*;
import com.goalracha.entity.Member;
import com.goalracha.entity.MemberRole;
import com.goalracha.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDTO getKakaoMember(String accessToken) {
        String email = getEmailFromKakaoAccessToken(accessToken);
        log.info("email: " + email);
        Member result = memberRepository.findByEmail(email);
        // 기존의 회원
        if (result != null) {
            MemberDTO memberDTO = MemberDTO.entityToDTO(result);
            return memberDTO;
        }
        log.info("유저 생성합니다");

        Member member = createKkoMember(email);
        memberRepository.save(member);
        log.info(member.toString());
        MemberDTO memberDTO = new MemberDTO(
                member.getUNo(),
                member.getUserId(),
                member.getPw(),
                member.getNickname(),
                member.getName(),
                member.getTel(),
                member.getCreateDate(),
                member.getEmail(),
                member.getBusinessName(),
                member.getBusinessId(),
                member.getState(),
                member.getType());
        return memberDTO;
    }

    @Override   // 사업자 담당자 이름, 연락처 변경
    public void ownerNameModify(OwnerNameModifyDTO ownerNameModifyDTO) {
        // 회원 번호로 회원 조회
        Optional<Member> result = memberRepository.findById(ownerNameModifyDTO.getUNo());

        // 조회된 회원이 존재하지 않으면 null을 반환합니다.
        Member member = result.orElse(null);

        // 조회된 회원이 없으면 메서드를 종료합니다.
        if (member == null) {
            return;
        }

        // 회원 정보를 수정합니다.
        member.ownerNameModify(ownerNameModifyDTO.getUNo(), ownerNameModifyDTO.getName(), ownerNameModifyDTO.getTel());

        // 수정된 회원 정보를 저장합니다.
        memberRepository.save(member);
    }

    @Override
    public void ownerPwModify(OwnerPwModifyDTO ownerPwModifyDTO) {
        // 회원 번호로 회원 조회
        Optional<Member> result = memberRepository.findById(ownerPwModifyDTO.getUNo());

        // 조회된 회원이 존재하지 않으면 null을 반환합니다.
        Member member = result.orElse(null);

        // 조회된 회원이 없으면 메서드를 종료합니다.
        if (member == null) {
            return;
        }

        // 기존 비밀번호와 수정하려는 비밀번호를 비교하여 동일한지 검사합니다.
        if (passwordEncoder.matches(ownerPwModifyDTO.getOldpw(), member.getPw())) {
            // 동일하다면 새로운 비밀번호로 회원 정보를 수정합니다.
            String encodedNewPassword = passwordEncoder.encode(ownerPwModifyDTO.getNewpw());
            member.setPw(encodedNewPassword);

            // 수정된 회원 정보를 저장합니다.
            memberRepository.save(member);
        } else {
            // 비밀번호가 일치하지 않는 경우에 대한 처리를 수행할 수 있습니다.
            // 예를 들어, 예외를 던지거나 메시지를 반환하여 클라이언트에게 알릴 수 있습니다.
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }
    }


    @Override   // 개인회원 닉네임, 연락처 변경
    public void userModify(MemberModifyDTO memberModifyDTO) {

        // 회원 번호로 회원을 조회합니다.
        Optional<Member> result = memberRepository.findById(memberModifyDTO.getUNo());

        // 조회된 회원이 존재하지 않으면 null을 반환합니다.
        Member member = result.orElse(null);

        // 조회된 회원이 없으면 메서드를 종료합니다.
        if (member == null) {
            return;
        }

        // 회원 정보를 수정합니다.
        member.userModify(memberModifyDTO.getUNo(), memberModifyDTO.getNickname(), memberModifyDTO.getTel());

        // 수정된 회원 정보를 저장합니다.
        memberRepository.save(member);
    }


    @Override
    public void modifyMember(MemberJoinDTO memberJoinDTO) {
        Member member = memberRepository.findByEmail(memberJoinDTO.getEmail());
//        MemberDTO edit = MemberDTO.entityToDTO(member);
//        edit.setName(memberJoinDTO.getName());
//        edit.setNickname(memberJoinDTO.getNickname());
//        edit.setTel(memberJoinDTO.getTel());

        member.joinMember(memberJoinDTO.getUNo(), memberJoinDTO.getName(), memberJoinDTO.getNickname(), memberJoinDTO.getTel());


        log.info("member info::" + member.toString());
        memberRepository.save(member);
    }

    @Override
    public boolean checkId(String userid) {
        log.info(userid);
        Member member = memberRepository.findByUserId(userid);
        if (member == null) {
            return false;
        }

        return true;
    }

    // 닉네임 중복검사
    @Override
    public boolean checkNickname(String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        return memberRepository.existsByNickname(nickname);
    }

    @Override
    public Long ownerJoin(OwnerJoinDTO joinDto) {
        Member member = Member.builder()
                .type(MemberRole.OWNER)
                .name(joinDto.getName())
                .email(joinDto.getEmail())
                .tel(joinDto.getTel())
                .businessId(joinDto.getBusiness_id())
                .businessName(joinDto.getBusiness_name())
                .userId(joinDto.getId())
                .pw(passwordEncoder.encode(joinDto.getPw()))
                .createDate(new Date())
                .build();
        Member result = memberRepository.save(member);
        log.info("user 생성 : :" + result.toString());
        return result.getUNo();
    }

    private Member createKkoMember(String email) { //카카오 유저 빌더

        Member member = Member.builder().email(email).userId(email).nickname(email).pw("null").type(MemberRole.USER).createDate(new Date()).state(1).build();
        return member;
    }

    private String getEmailFromKakaoAccessToken(String accessToken) { //카카오토큰으로 유저 이메일 리턴
        String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";
        if (accessToken == null) {
            throw new RuntimeException("Access Token is null");
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoGetUserURL).build();
        ResponseEntity<LinkedHashMap> response = restTemplate.exchange(uriBuilder.toString(), HttpMethod.GET, entity,
                LinkedHashMap.class);
        log.info(response);
        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();
        log.info("------------------------------------");
        log.info(bodyMap);
        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("kakao_account");
        log.info("kakaoAccount: " + kakaoAccount);
        return kakaoAccount.get("email");
    }

    @Override
    public List<MemberDTO> findMembersByType(MemberRole type) {
        List<Member> members = memberRepository.findByType(type);
        return members.stream()
                .map(member -> MemberDTO.entityToDTO(member)) // ModelMapper 대신 사용
                .collect(Collectors.toList());
    }

    // 회원 탈퇴
    @Override
    public void withdrawMember(Long uNo) {
        // 회원 조회
        Optional<Member> memberOptional = memberRepository.findById(uNo);

        // 회원이 존재하면
        memberOptional.ifPresent(member -> {
            // 회원의 예약 내역 수를 조회합니다.
            int reservationCount = memberRepository.countReservationsAfterToday(uNo);

            // 예약 내역이 없는 경우 회원의 상태를 탈퇴로 변경합니다.
            if (reservationCount == 0) {
                // 회원 상태 변경 메서드 호출
                member.delete();
                memberRepository.save(member); // 변경된 상태를 저장
                log.info("회원 탈퇴가 성공적으로 수행되었습니다.");
            } else {
                // 예약 내역이 있는 경우 처리할 내용을 기록합니다.
                log.info("회원은 아직 예약 내역이 남아 있어 탈퇴가 불가능합니다.");
                // 예약 내역이 있을 경우에 대한 예외 처리 또는 메시지 출력 등을 수행할 수 있습니다.
            }
        });
    }

}
