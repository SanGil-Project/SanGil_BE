package com.project.sangil_be.feed.controller;

import com.project.sangil_be.feed.dto.FeedDetailResponseDto;
import com.project.sangil_be.feed.dto.FeedListResponseDto;
import com.project.sangil_be.feed.dto.FeedResponseDto;
import com.project.sangil_be.feed.dto.GoodCheckResponseDto;
import com.project.sangil_be.model.Feed;
import com.project.sangil_be.model.User;
import com.project.sangil_be.feed.repository.FeedRepository;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class FeedController {
    private final FeedService feedService;
    private final FeedRepository feedRepository;

    //피드 작성
    @PostMapping("/feeds/write")
    public FeedResponseDto save(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("feedContent") String feedContent,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return feedService.saveFeed(feedContent, multipartFile, userDetails);
    }


    @GetMapping("/feeds/detail/{feedId}/{pageNum}")
    public FeedDetailResponseDto detail(@PathVariable("feedId") Long feedId, @PathVariable("pageNum") int pageNum, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return feedService.feedDetail(feedId, userDetails,pageNum-1);
    }

    // 피드 리스트
    @GetMapping("/feeds/{pageNum}")
    public FeedListResponseDto mainFeed(@PathVariable("pageNum") int pageNum,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return feedService.mainFeed(userDetails,pageNum-1);
    }

    //나의 피드
    @GetMapping("/myfeeds/{pageNum}")
    public FeedListResponseDto myfeeds (@PathVariable("pageNum") int pageNum, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return feedService.myfeeds(userDetails, pageNum-1);
    }

    //피드 좋아요
    @PostMapping("/feeds/good/{feedId}")
    public GoodCheckResponseDto goodCheck(@PathVariable("feedId") Long feedId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return feedService.goodCheck(feedId, userDetails);
    }

    //피드 삭제
    @Transactional
    @DeleteMapping("/feeds/delete/{feedId}")
    public void deleteFeed(@PathVariable("feedId") Long feedId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        feedService.deletefeed(feedId, userDetails);
    }
}
