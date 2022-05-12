package com.project.sangil_be.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class Top10MountainDto {
    private Long mountainId;
    private String mountain;
    private String mountainAddress;
    private String mountainImgUrl;
    private Boolean bookmark;
    private String starAvr;

    public Top10MountainDto(Map<String, Object> top10Mountain, Boolean bookMark) {
        this.mountainId = Long.valueOf(String.valueOf(top10Mountain.get("mountain_id")));
        this.mountain = String.valueOf(top10Mountain.get("mountain"));
        this.mountainAddress = String.valueOf(top10Mountain.get("mountain_address"));
        this.mountainImgUrl = String.valueOf(top10Mountain.get("mountain_img_url"));
        this.bookmark = bookMark;
        this.starAvr = String.format("%.1f", top10Mountain.get("avrStar"));
    }
}
