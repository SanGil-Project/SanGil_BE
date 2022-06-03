package com.project.sangil_be.mypage.controller;

import com.project.sangil_be.feed.dto.FeedResponseDto;
import com.project.sangil_be.login.dto.UserResponseDto;
import com.project.sangil_be.login.dto.UsernameRequestDto;
import com.project.sangil_be.model.User;
import com.project.sangil_be.mountain.dto.BookMarkDto;
import com.project.sangil_be.mypage.dto.*;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    // 맵트래킹 마이페이지
    @GetMapping("/mypage/tracking")
    public List<CompletedListDto> myPageTracking(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.myPageTracking(userDetails);
    }

    // 맵트래킹 마이페이지 산 선택
    @GetMapping("/mypage/tracking/{mountainId}")
    public List<CompletedMountainDto> selectMountain(@PathVariable Long mountainId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.selectMountain(mountainId,userDetails);
    }

    // 닉네임 중복체크
    @PostMapping("/mypage/nameCheck")
    public Boolean usernameCheck (@RequestBody UsernameRequestDto usernameRequestDto){
        return mypageService.usernameCheck(usernameRequestDto);
    }

    // nickname 수정
    @PutMapping("/mypage/profilename")
    public UserResponseDto editname(@RequestBody UsernameRequestDto usernameRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.editname(usernameRequestDto, userDetails);
    }

    // userimageUrl 수정
    @PutMapping("/mypage/profileUrl")
    public void editimage(@RequestParam("file") MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetails){
        mypageService.editimage(multipartFile, userDetails);
    }

    // 마이페이지 즐겨찾기한 산
    @GetMapping("/mypage/bookmark/{pageNum}")
    public BookMarkDto getBookMarkMountain (@PathVariable int pageNum, @RequestParam double lat, @RequestParam double lng, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.getBookMarkMountain(lat,lng,userDetails,pageNum-1);
    }

    // 마이페이지 피드 10개
    @GetMapping("/mypage/myfeeds")
    public List<FeedResponseDto> myFeeds (@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.myFeeds(userDetails);
    }

    // 칭호 리스트
    @GetMapping("/mypage/userTitle")
    public UserTitleResponseDto getUserTitle(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.getUserTitle(userDetails);
    }

    // 칭호 변경
    @PutMapping("/mypage/userTitle")
    public ChangeTitleDto putUserTitle(@RequestBody ChangeTitleRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mypageService.putUserTitle(requestDto, userDetails);
    }
}
