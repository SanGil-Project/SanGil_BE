package com.project.sangil_be.controller;

import com.project.sangil_be.dto.FeedListResponseDto;
import com.project.sangil_be.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MainController {
    private final FeedService feedService;

    //피드 메인페이지
    @GetMapping("/api/main/feeds/{pageNum}")
    public FeedListResponseDto getFeeds(@PathVariable int pageNum) {
        return new FeedListResponseDto(feedService.getFeeds(pageNum-1));
    }
}
