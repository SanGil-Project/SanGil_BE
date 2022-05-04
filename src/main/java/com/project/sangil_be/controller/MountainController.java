package com.project.sangil_be.controller;

import com.project.sangil_be.dto.PartyListResponseDto;
import com.project.sangil_be.dto.SearchBeforeDto;
import com.project.sangil_be.service.MountainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MountainController {
    private final MountainService mountainService;

    @GetMapping("/api/mountain/search/before")
    public List<SearchBeforeDto.MountainInfo> getAllParty() {
        return mountainService.Show10();
    }
}
