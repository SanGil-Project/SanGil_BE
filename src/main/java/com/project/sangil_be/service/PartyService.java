package com.project.sangil_be.service;

import com.project.sangil_be.dto.PartyDetailDto;
import com.project.sangil_be.dto.PartyListDto;
import com.project.sangil_be.dto.PartyRequestDto;
import com.project.sangil_be.model.Attend;
import com.project.sangil_be.model.Party;
import com.project.sangil_be.model.User;
import com.project.sangil_be.repository.AttendRepository;
import com.project.sangil_be.repository.PartyRepository;
import com.project.sangil_be.repository.UserRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PartyService {
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;
    private final AttendRepository attendRepository;
    private final Validator validator;

    // 등산 모임 참가 작성
    @Transactional
    public PartyListDto writeParty(UserDetailsImpl userDetails, PartyRequestDto partyRequestDto) throws IOException {
        //내용이 입력되어 있는지 확인
        validator.blankContent(partyRequestDto);

        //만든사람 ID값 추가시 필요한 메소드
        User user = userRepository.findById(userDetails.getUser().getUserId()).orElse(null);

        //만들어지면 처음 모임 인원 수는 1
        int curPeople = 1;

        //컴플리트 기본은 true
        boolean completed = true;

        //Party에 작성한 내용 및 현재 모집인원 수 추가
        Party party = new Party(partyRequestDto.getTitle(), partyRequestDto.getMountain(),
                                partyRequestDto.getAddress(), partyRequestDto.getPartyDate(),
                                partyRequestDto.getPartyTime(), partyRequestDto.getMaxPeople(),
                                curPeople, partyRequestDto.getPartyContent(), completed, user);

        //레파지토리에 저장
        partyRepository.save(party);

        //참여하기에 생성한 유저의 내용 저장
        Attend attend = new Attend(userDetails.getUser().getUserId(), party.getPartyId());

        attendRepository.save(attend);

        return new PartyListDto(party.getPartyId(), party.getUser().getUsername(), party.getTitle(),
                                party.getPartyContent(), party.getMountain(), party.getAddress(),
                                party.getPartyDate(), party.getPartyTime(), party.getMaxPeople(),
                                party.getCurPeople(), completed, party.getCreatedAt());
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

        return validator.overPages(partyListDto, start, end, pageable, pageNum);
    }


    private void setPartyList(List<Party> partyList, List<PartyListDto> partyListDto) {
        for (Party party : partyList) {
            boolean completed = true;
            if(party.getMaxPeople() <= party.getCurPeople()) {
                completed = false;
            }

            PartyListDto partyDto = new PartyListDto(party.getPartyId(), party.getUser().getUsername(), party.getTitle(),
                                                     party.getPartyContent(), party.getMountain(), party.getAddress(),
                                                     party.getPartyDate(), party.getPartyTime(), party.getMaxPeople(),
                                                     party.getCurPeople(), completed, party.getCreatedAt());

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
        Party party = partyRepository.findById(partyId).orElse(null);;
        PartyDetailDto partyDetailDto = new PartyDetailDto(party.getPartyId(), party.getTitle(), party.getMountain(),
                                                           party.getAddress(), party.getPartyDate(), party.getMaxPeople(),
                                                           party.getCurPeople(), party.getPartyContent(), party.getCreatedAt());

        return partyDetailDto;
    }

    // api에 맞게 수정 필요
    // 동호회 수정 코드
    @Transactional
    public PartyDetailDto updateParty(Long partyId, PartyRequestDto partyRequestDto) {
        Party party = partyRepository.findById(partyId).orElseThrow(
                ()-> new IllegalArgumentException("찾는 게시글이 없습니다.")
        );
        party.update(partyRequestDto.getPartyDate(), partyRequestDto.getPartyTime(),
                     partyRequestDto.getMaxPeople(), partyRequestDto.getPartyContent());

        PartyDetailDto partyDetailDto = new PartyDetailDto(party.getPartyId(), party.getTitle(), party.getMountain(),
                                                           party.getAddress(), party.getPartyDate(), party.getMaxPeople(),
                                                           party.getCurPeople(), party.getPartyContent(), party.getCreatedAt());
        return partyDetailDto;
    }

    // 동호회 모임 삭제 코드
    @Transactional
    public void deleteParty(Long partyId) {
        try {
            partyRepository.deleteById(partyId);
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("게시글을 삭제하지 못했습니다.");
        }
    }

    //동호회 참여하기 기능 구현
    public String attendParty(Long partyId, UserDetailsImpl userDetails) {
        Attend attend = attendRepository.findByPartyId(partyId);
        Party party = partyRepository.findById(partyId).orElseThrow(
                ()-> new IllegalArgumentException("참여할 동호회 모임이 없습니다.")
        );

        //중복 참여 제한
        if(attend.getUserId().equals(userDetails.getUser().getUserId())) {
            return "중복참여를 할 수 없습니다!";
        }else{
            Attend saveAttend = new Attend(userDetails.getUser().getUserId(), partyId);
            int result = party.getCurPeople() + 1;
            party.updateCurpeople(result);
            attendRepository.save(saveAttend);
            return "true";
        }
    }
}