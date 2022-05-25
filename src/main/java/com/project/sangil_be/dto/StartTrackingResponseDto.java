package com.project.sangil_be.dto;

import lombok.Getter;

@Getter
public class StartTrackingResponseDto {
    private Long completedId;
    private String mountainImgUrl;

    public StartTrackingResponseDto(Long completeId, String mountainImgUrl) {
        this.completedId = completeId;
        this.mountainImgUrl = mountainImgUrl;
    }
}
