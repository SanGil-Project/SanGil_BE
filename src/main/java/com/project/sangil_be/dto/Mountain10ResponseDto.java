package com.project.sangil_be.dto;

import com.project.sangil_be.model.Mountain100;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Mountain10ResponseDto {

    private Long mountainId;
    private String mountainName;
    private String mountainImgUrl;
    private String mountainAddress;
    private String starAvr;
    private boolean bookmark;
    private int count;



    public Mountain10ResponseDto(Mountain100 mountain100,String starAvr,Boolean bookmark, int cnt) {
        this.mountainId = mountain100.getMountain100Id();
        this.mountainName = mountain100.getMountain();
        this.mountainImgUrl = mountain100.getMountainImgUrl();
        this.mountainAddress = mountain100.getMountainAddress();
        this.starAvr = starAvr;
        this.bookmark = bookmark;
        this.count = cnt;
    }
}
