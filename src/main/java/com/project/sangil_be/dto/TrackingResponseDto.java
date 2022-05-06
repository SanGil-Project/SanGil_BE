package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackingResponseDto {
    private String lat;
    private String lng;
    private String timer;

    public TrackingResponseDto(String lat, String lng, String timer) {
        this.lat = lat;
        this.lng = lng;
        this.timer = timer;
    }
}
