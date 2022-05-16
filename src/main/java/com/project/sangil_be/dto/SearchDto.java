package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class SearchDto {

    private Long mountainId;
    private String mountain;
    private String mountainAddress;
    private String mountainImgUrl;
    private String starAvr;
    private Double lat;
    private Double lng;

    public SearchDto(Long mountainId, String mountain, String mountainAddress, String mountainImgUrl, String starAvr, Double lat, Double lng) {
        this.mountainId = mountainId;
        this.mountain = mountain;
        this.mountainAddress = mountainAddress;
        this.mountainImgUrl = mountainImgUrl;
        this.starAvr = starAvr;
        this.lat = lat;
        this.lng = lng;
    }

//    public SearchDto(String starAvr, Mountain100Dto mountain100Dto) {
//        this.mountainId=mountain100Dto.getMountainId();
//        this.mountain=mountain100Dto.getMountain();
//        this.mountainAddress=mountain100Dto.getMountainAddress();
//        this.mountainImgUrl=mountain100Dto.getMountainImgUrl();
//        this.starAvr=starAvr;
//        this.lat=mountain100Dto.getLat();
//        this.lng=mountain100Dto.getLng();
//    }

    public SearchDto(Map<String, Object> stringObjectMap) {
        this.mountainId = Long.valueOf(String.valueOf(stringObjectMap.get("mountain_id")));
        this.mountain = String.valueOf(stringObjectMap.get("mountain"));
        this.mountainAddress = String.valueOf(stringObjectMap.get("mountain_address"));
        this.mountainImgUrl = String.valueOf(stringObjectMap.get("mountain_img_url"));
        this.starAvr = String.format("%.1f", stringObjectMap.get("avrStar"));
        this.lat = Double.valueOf(String.valueOf(stringObjectMap.get("lat")));
        this.lng = Double.valueOf(String.valueOf(stringObjectMap.get("lng")));
    }
}

