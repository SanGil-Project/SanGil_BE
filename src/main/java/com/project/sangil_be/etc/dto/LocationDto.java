package com.project.sangil_be.etc.dto;

import lombok.Getter;

@Getter
public class LocationDto {

    private Double lat;
    private Double lng;

    public LocationDto(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}