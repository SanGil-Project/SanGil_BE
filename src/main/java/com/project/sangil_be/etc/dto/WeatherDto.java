package com.project.sangil_be.etc.dto;

import lombok.Getter;

@Getter
public class WeatherDto {
    private int weather;
    private String weatherImageUrl;

    public WeatherDto(int currentTemp, String weatherUrl) {
        this.weather = currentTemp;
        this.weatherImageUrl = weatherUrl;
    }
}
