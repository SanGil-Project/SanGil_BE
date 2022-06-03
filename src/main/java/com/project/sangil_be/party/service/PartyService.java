package com.project.sangil_be.party.service;

import com.project.sangil_be.model.User;
import com.project.sangil_be.login.repository.UserRepository;
import com.project.sangil_be.model.*;
import com.project.sangil_be.mypage.dto.TitleDto;
import com.project.sangil_be.party.dto.*;
import com.project.sangil_be.party.repository.AttendRepository;
import com.project.sangil_be.party.repository.PartyRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.etc.utils.TitleUtil;
import com.project.sangil_be.etc.utils.Calculator;
import com.project.sangil_be.etc.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PartyService {
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final AttendRepository attendRepository;
    private final Validator validator;
    private final TitleUtil titleService;
    private final Calculator calculator;

    // 등산 모임 참가 작성
    @Transactional
    public PartyListDto writeParty(UserDetailsImpl userDetails, PartyRequestDto partyRequestDto) throws IOException {
        validator.blankContent(partyRequestDto); //내용이 입력되어 있는지 확인
        User user = userRepository.findById(userDetails.getUser().getUserId()).orElse(null); //만든사람 ID값 추가시 필요한 메소드
        int currentPeople = 1; //만들어지면 처음 모임 인원 수는 1
        boolean completed = true; //컴플리트 기본은 true
        Party party = new Party(partyRequestDto, currentPeople, completed, user); //Party에 작성한 내용 및 현재 모집인원 수 추가
        partyRepository.save(party); //레파지토리에 저장
        Attend attend = new Attend(userDetails.getUser().getUserId(), party.getPartyId()); //참여하기에 생성한 유저의 내용 저장
        attendRepository.save(attend);
        List<TitleDto> titleDtoList = titleService.getAttendTitle(userDetails);
        Long beforeTime = ChronoUnit.MINUTES.between(party.getCreatedAt(), LocalDateTime.now());
        return new PartyListDto(party, completed, titleDtoList,calculator.time(beforeTime));
    }

    // complete 수정 필요
    // 모든 동호회 리스트 가져오기
    @Transactional
    public Page<PartyListDto> getAllParty(int pageNum) {
        List<Party> partyList = partyRepository.findAllByOrderByCreatedAtDesc();
        Pageable pageable = getPageable(pageNum);
        List<PartyListDto> partyListDto = new ArrayList<>();
        setPartyList(partyList, partyListDto);

        int start = pageNum * 6;
        int end = Math.min((start + 6), partyList.size());


        return validator.overPagesParty(partyListDto, start, end, pageable, pageNum);
    }

    private void setPartyList(List<Party> partyList, List<PartyListDto> partyListDto) {
        for (Party party : partyList) {
            boolean completed = true;

            if (party.getMaxPeople() <= party.getCurPeople()) {
                completed = false;
            }
            long beforeTime = ChronoUnit.MINUTES.between(party.getCreatedAt(), LocalDateTime.now());
            PartyListDto partyDto = new PartyListDto(party, completed, calculator.time(beforeTime));
            partyListDto.add(partyDto);
        }
    }

    // 페이지 정렬
    private Pageable getPageable(int page) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        return PageRequest.of(page, 6, sort);
    }

    // 동호회 상세페이지
    @Transactional
    public PartyDetailDto findParty(Long partyId) {
        List<Attend> attend = attendRepository.findByPartyId(partyId);
        List<PartymemberDto> partymemberDto = new ArrayList<>();

        Party party = partyRepository.findById(partyId).orElse(null);
        ;
        boolean completed;
        for (Attend attends : attend) {
            User user = userRepository.findByUserId(attends.getUserId());
            partymemberDto.add(new PartymemberDto(user));
        }
        long beforeTime = ChronoUnit.MINUTES.between(party.getCreatedAt(), LocalDateTime.now());

        if (party.getMaxPeople() <= party.getCurPeople()) {
            completed = false;
        } else {
            completed = true;
        }
        return new PartyDetailDto(party, partymemberDto, completed, calculator.time(beforeTime));
    }

    // api에 맞게 수정 필요
    // 동호회 수정 코드
    @Transactional
    public PartyDetailDto updateParty(Long partyId, PartyRequestDto partyRequestDto, UserDetailsImpl userDetails) {

        validator.partyAuthCheck(partyId,userDetails.getUser().getUserId());
        boolean completed;
        Party party = partyRepository.findById(partyId).orElseThrow(
                () -> new IllegalArgumentException("찾는 게시글이 없습니다.")
        );
        if (partyRequestDto.getMaxPeople() <= party.getCurPeople()) {
            completed = false;
        } else {
            completed = true;
        }
        party.update(partyRequestDto.getPartyDate(), partyRequestDto.getPartyTime(),
                partyRequestDto.getMaxPeople(), partyRequestDto.getPartyContent(),completed);

        Long beforeTime = ChronoUnit.MINUTES.between(party.getCreatedAt(), LocalDateTime.now());

        return new PartyDetailDto(party,calculator.time(beforeTime));
    }

    // 동호회 모임 삭제 코드
    @Transactional
    public void deleteParty(Long partyId, UserDetailsImpl userDetails) {
        try {
            validator.partyAuthCheck(partyId,userDetails.getUser().getUserId());
            partyRepository.deleteById(partyId);
            attendRepository.deleteByPartyIdAndUserId(partyId, userDetails.getUser().getUserId());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("게시글을 삭제하지 못했습니다.");
        }
    }

    //동호회 참여하기 기능 구현
    @Transactional
    public TitleResponseDto attendParty(Long partyId, UserDetailsImpl userDetails) {
        Attend attend = attendRepository.findByPartyIdAndUserId(partyId, userDetails.getUser().getUserId());
        Party party = partyRepository.findById(partyId).orElseThrow(
                () -> new IllegalArgumentException("참여할 동호회 모임이 없습니다.")
        );

        List<TitleDto> titleDtoList = titleService.getAttendTitle(userDetails);

        //중복 참여 제한
        String msg;
        if (attend == null) {
            Attend saveAttend = new Attend(userDetails.getUser().getUserId(), partyId);
            int result = party.getCurPeople() + 1;
            party.updateCurpeople(result);
            attendRepository.save(saveAttend);
            msg = "true";
        } else {
            attendRepository.deleteByPartyIdAndUserId(partyId, userDetails.getUser().getUserId());
            int result = party.getCurPeople() - 1;
            party.updateCurpeople(result);
            msg = "false";
        }
        return new TitleResponseDto(titleDtoList, msg);
    }

    // 동호회 검색
    public Page<PartyListDto> getSearch(String keyword, int pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum, 6);
        Page<PartyListDto> partyListDtos = partyRepository.searchPage(keyword, pageRequest);
        LocalDate date = LocalDate.now();
        for (PartyListDto partyListDto : partyListDtos) {
            LocalDate date1 = LocalDate.parse(partyListDto.getPartyDate());
            Long beforeTime = ChronoUnit.MINUTES.between(partyListDto.getCreatedAt(), LocalDateTime.now());
            partyListDto.setBeforeTime(calculator.time(beforeTime));
            if (date.isAfter(date1) || date.isEqual(date1)) {
                partyListDto.setPartyDate("마감되었습니다.");
            }
            if (partyListDto.getMaxPeople() <= partyListDto.getCurrentPeople()) {
                partyListDto.setCompleted(false);
            }else {
                partyListDto.setCompleted(true);
            }
        }
        return partyListDtos;
    }
}