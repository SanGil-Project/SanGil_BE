package com.project.sangil_be.etc.controller;

import com.project.sangil_be.etc.utils.geocoder.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final ServiceImpl service;

    // 데이터베이스에 산 좌표 주입
    @PutMapping("/api/test")
    public void updateXY() {
        service.updateXY();
    }
}
