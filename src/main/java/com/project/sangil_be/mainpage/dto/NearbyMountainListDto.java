package com.project.sangil_be.mainpage.dto;

import com.project.sangil_be.model.Mountain;
import lombok.Data;
import lombok.Getter;

@Data
public class NearbyMountainListDto {
    private Long mountainId;
    private String mountain;
    private String mountainAddress;
    private String mountainImgUrl;
    private Double starAvr;
    private Boolean bookmark;
    private Double distance;

    public NearbyMountainListDto(Mountain mountain, Double starAvr, Boolean bookmark, Double dis) {
        this.mountainId=mountain.getMountainId();
        this.mountain=mountain.getMountain();
        this.mountainImgUrl=mountain.getMountainImgUrl();
        this.mountainAddress=mountain.getMountainAddress();
        this.starAvr=starAvr;
        this.bookmark=bookmark;
        this.distance=dis;
    }

    //query dsl (bookmark, distance -> service단에서 set으로 나감)
    public NearbyMountainListDto(Long mountainId, String mountain, String mountainImgUrl, String mountainAddress, Double starAvr) {
        this.mountainId = mountainId;
        this.mountain = mountain;
        this.mountainImgUrl = mountainImgUrl;
        this.mountainAddress = mountainAddress;
        this.starAvr = starAvr;
    }
}
