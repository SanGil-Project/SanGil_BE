package com.project.sangil_be.tracking.controller;

import com.project.sangil_be.mypage.dto.CompleteRequestDto;
import com.project.sangil_be.party.dto.TitleResponseDto;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.tracking.service.TrackingService;
import com.project.sangil_be.tracking.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TrackingController {
    private final TrackingService trackingService;

    // 트래킹 시작
    @PostMapping("/tracking/{mountainId}")
    public StartTrackingResponseDto startMyLocation (
            @PathVariable Long mountainId,
            @RequestBody StartTrackingRequestDto startTrackingRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return trackingService.startMyLocation(mountainId,startTrackingRequestDto, userDetails);
    }

    // 맵 트래킹 3초 마다 저장
    @PostMapping("/tracking/mountain/{completedId}")
    public DistanceResponseDto saveMyLocation (@PathVariable Long completedId, @RequestBody TrackingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return trackingService.saveMyLocation(completedId, requestDto, userDetails);
    }

    // 트래킹 완료 후 저장
    @PutMapping("/tracking/{completedId}")
    public TitleResponseDto saveTracking(@PathVariable Long completedId, @RequestBody CompleteRequestDto completeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return trackingService.saveTracking(completedId,completeRequestDto,userDetails);
    }

    // 맵트래킹 삭제 (10분 이하)
    @DeleteMapping("/tracking/{completedId}")
    public void deleteTracking(@PathVariable Long completedId){
        trackingService.deleteTracking(completedId);
    }

    // 맵 트래킹 상세페이지
    @GetMapping("/tracking/detail/{completedId}")
    public TrackingListDto detailTracking (@PathVariable Long completedId) {
        return trackingService.detailTracking(completedId);
    }

}
