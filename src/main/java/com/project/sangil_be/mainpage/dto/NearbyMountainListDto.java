package com.project.sangil_be.mainpage.dto;

import com.project.sangil_be.model.Mountain;
import lombok.Data;
import lombok.Getter;

@Data
public class NearbyMountainListDto {
    private Long mountainId;
    private String mountainName;
    private String mountainImgUrl;
    private String mountainAddress;
    private Double starAvr;
    private boolean bookmark;
    private Double distance;

    public NearbyMountainListDto(Mountain mountain, Double starAvr, Boolean bookmark, Double dis) {
        this.mountainId=mountain.getMountainId();
        this.mountainName=mountain.getMountain();
        this.mountainImgUrl=mountain.getMountainImgUrl();
        this.mountainAddress=mountain.getMountainAddress();
        this.starAvr=starAvr;
        this.bookmark=bookmark;
        this.distance=dis;
    }

    public NearbyMountainListDto(Long mountainId, String mountainName, String mountainImgUrl, String mountainAddress, Double starAvr) {
        this.mountainId = mountainId;
        this.mountainName = mountainName;
        this.mountainImgUrl = mountainImgUrl;
        this.mountainAddress = mountainAddress;
        this.starAvr = starAvr;
    }
}
