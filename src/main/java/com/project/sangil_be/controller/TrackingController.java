//package com.project.sangil_be.controller;
//
//import com.project.sangil_be.dto.TrackingListDto;
//import com.project.sangil_be.dto.TrackingRequestDto;
//import com.project.sangil_be.dto.TrackingResponseDto;
//import com.project.sangil_be.securtiy.UserDetailsImpl;
//import com.project.sangil_be.service.TrackingService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RestController
//public class TrackingController {
//    private final TrackingService trackingService;
//
//    //트랙킹 시작 시 사용자 위치 가져오는 api
//    @PostMapping("/api/mountain/tracking")
//    public void saveMyLocation (@RequestBody TrackingRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        trackingService.saveMyLocation(requestDto, userDetails);
//    }
//
//    //트랙킹 끝난 후 트랙킹 위도,경도 전부 프론트에 보내주는 api
//    @GetMapping("/api/mountain/tracking")
//    public TrackingListDto getAllLocation (@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return trackingService.getAllLocation(userDetails);
//    }
//}
