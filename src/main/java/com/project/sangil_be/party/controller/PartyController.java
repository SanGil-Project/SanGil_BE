package com.project.sangil_be.party.controller;

import com.project.sangil_be.party.dto.*;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PartyController {
    private final PartyService partyService;

    //동호회 모임 만들기
    @PostMapping("/party/write")
    public PartyListDto writeParty(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PartyRequestDto partyRequestDto) throws IOException {
        return partyService.writeParty(userDetails, partyRequestDto);
    }

    //모든 동호회 찾아오기
    @GetMapping("/parties/{pageNum}")
    public PartyListResponseDto getAllParty(@PathVariable int pageNum) {
        return new PartyListResponseDto(partyService.getAllParty(pageNum-1));
    }

    //모든 동호회 검색
    @GetMapping("/parties/search")
    public PartyListResponseDto getSearch(@RequestParam(value = "keyword") String keyword,
                                          @RequestParam("pageNum") int pageNum) {
        return new PartyListResponseDto(partyService.getSearch(keyword,pageNum-1));
    }

    //동호회 상세 페이지
    @GetMapping("/party/{partyId}")
    public PartyDetailDto findParty (@PathVariable Long partyId) {
        return partyService.findParty(partyId);
    }


    //동호회 모임 참가하기
    @PostMapping("/party/attend/{partyId}")
    public TitleResponseDto attendParty (@PathVariable Long partyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return partyService.attendParty(partyId, userDetails);
    }

    //동호회 내용 수정
    @PutMapping("/party/{partyId}")
    public PartyDetailDto updateParty (@PathVariable Long partyId, @RequestBody PartyRequestDto partyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return partyService.updateParty(partyId, partyRequestDto, userDetails);
    }

    //동호회 내용 삭제
    @DeleteMapping("/party/{partyId}")
    public void deleteParty (@PathVariable Long partyId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        partyService.deleteParty(partyId,userDetails);
    }
}
