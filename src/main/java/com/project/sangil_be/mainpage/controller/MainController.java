package com.project.sangil_be.mainpage.controller;

import com.project.sangil_be.feed.dto.FeedListResponseDto;
import com.project.sangil_be.feed.dto.FeedResponseDto;
import com.project.sangil_be.mainpage.dto.NearbyMountainDto;
import com.project.sangil_be.mainpage.dto.PlanListDto;
import com.project.sangil_be.mainpage.dto.TwoPartyListDto;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.mainpage.dto.Top10MountainDto;
import com.project.sangil_be.mainpage.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MainController {
    private final MainService mainService;

    // 메인/마이페이지 예정된 등산 모임 임박한 순
    @GetMapping("/plan")
    public List<PlanListDto> getPlan(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mainService.getPlan(userDetails);
    }

    // 메인페이지 최신 2개 모임
    @GetMapping("/main/parties")
    public List<TwoPartyListDto> getTwoParty(){
        return mainService.getTwoParty();
    }

    // 메인페이지 북마크 순으로 탑10
    @GetMapping("/main/mountains")
    public List<Top10MountainDto>  get10Mountains(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mainService.get10Mountains(userDetails);
    }

    // 자기 주변 산
    @GetMapping("/main/nearby/{pageNum}")
    public NearbyMountainDto nearby(@RequestParam double lat, @RequestParam double lng, @PathVariable("pageNum")int pageNum, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mainService.nearby(lat,lng,pageNum-1,userDetails);
    }

    // 메인 페이지 피드 7개
    @GetMapping("/main/feeds")
    public List<FeedResponseDto> mainfeeds (@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mainService.mainfeeds(userDetails);
    }


}
