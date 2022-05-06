package com.project.sangil_be.controller;

import com.project.sangil_be.dto.FeedListResponseDto;
import com.project.sangil_be.dto.Mountain10ResponseDto;
import com.project.sangil_be.dto.PlanResponseDto;
import com.project.sangil_be.dto.TwoPartyListResponseDto;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.service.FeedService;
import com.project.sangil_be.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MainController {
    private final MainService mainService;
    private final FeedService feedService;

    //피드 메인페이지
    @GetMapping("/api/main/feeds/{pageNum}")
    public FeedListResponseDto getFeeds(@PathVariable int pageNum) {
        return new FeedListResponseDto(feedService.getFeeds(pageNum - 1));
    }

    @GetMapping("/api/plan")
    public PlanResponseDto getPlan(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mainService.getPlan(userDetails);
    }

    @GetMapping("/api/main/parties")
    public TwoPartyListResponseDto getTwoParty(){
        return mainService.getTwoParty();
    }

    @GetMapping("/api/main/mountains")
    public List<Mountain10ResponseDto> get10Mountains(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mainService.get10Mountains(userDetails);
    }
}
