package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackingRequestDto {
    private String lat;
    private String lng;
    private String timer;
}
