package com.project.sangil_be.dto;

import com.project.sangil_be.model.Mountain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMarkResponseDto {
    private Long mountainId;
    private String mountainName;
    private String mountainAddress;
    private String mountainImageUrl;
    private boolean bookMarkChk;
    private Double starAvr;
    private Double distance;


    public BookMarkResponseDto(Mountain mountain, boolean bookMarkChk, Double starAvr, Double distance) {
        this.mountainId = mountain.getMountainId();
        this.mountainName = mountain.getMountain();
        this.mountainAddress = mountain.getMountainAddress();
        this.mountainImageUrl = mountain.getMountainImgUrl();
        this.bookMarkChk = bookMarkChk;
        this.starAvr = Math.round(starAvr * 10) / 10.0;
        this.distance = Math.round(distance * 100) / 100.0;
    }

    public BookMarkResponseDto(Long mountainId, String mountainName, String mountainAddress, String mountainImageUrl, Double starAvr) {
        this.mountainId = mountainId;
        this.mountainName = mountainName;
        this.mountainAddress = mountainAddress;
        this.mountainImageUrl = mountainImageUrl;
        if (starAvr == null) {
            this.starAvr = 0d;
        } else {
            this.starAvr = starAvr;
        }
    }
}
