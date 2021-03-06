package com.project.sangil_be.mountain.controller;

import com.project.sangil_be.mountain.dto.MountainResponseDto;
import com.project.sangil_be.mountain.dto.SearchAfterDto;
import com.project.sangil_be.mountain.dto.SearchDto;
import com.project.sangil_be.securtiy.UserDetailsImpl;
import com.project.sangil_be.mountain.service.MountainService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MountainController {
    private final MountainService mountainService;

    // 검색 전 페이지
    @GetMapping("/mountain/search/before")
    public List<SearchDto> show10() {
        return mountainService.Show10();
    }

    // 검색 후 페이지
    @GetMapping("/mountain/search")
    public SearchAfterDto searchMountain(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam("pageNum") int pageNum){
        return new SearchAfterDto(mountainService.searchMountain(keyword,pageNum-1));
    }

    // 산 상세 페이지
    @GetMapping("/mountain/{mountainId}/{pageNum}")
    public MountainResponseDto detailMountain(
            @PathVariable Long mountainId,
            @PathVariable int pageNum,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException, ParseException {
        return mountainService.detailMountain(mountainId,pageNum-1,userDetails);
    }

    // 산 즐겨찾기
    @PostMapping("/mountain/bookmark/{mountainId}")
    public String myBookMark(@PathVariable Long mountainId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return mountainService.myBookMark(mountainId, userDetails);
    }
}
