package com.project.sangil_be.mountain.dto;

import com.project.sangil_be.model.Mountain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMarkResponseDto {
    private Long mountainId;
    private String mountain;
    private String mountainAddress;
    private boolean bookMark;
    private String starAvr;
    private Double distance;


//    public BookMarkResponseDto(Mountain mountain, boolean bookMarkChk, Double starAvr, Double distance) {
//        this.mountainId = mountain.getMountainId();
//        this.mountain = mountain.getMountain();
//        this.mountainAddress = mountain.getMountainAddress();
//        this.bookMark = bookMarkChk;
//        this.starAvr = Math.round(starAvr*10)/10.0;
//        this.distance = Math.round(distance*100)/100.0;
//    }

    public BookMarkResponseDto(Long mountainId, String mountain, String mountainAddress, Double starAvr) {
        this.mountainId = mountainId;
        this.mountain = mountain;
        this.mountainAddress = mountainAddress;
        if (starAvr == null) {
            this.starAvr = String.valueOf(0.0);
        } else {
            this.starAvr = String.format("%.1f", starAvr);
        }
    }
}
