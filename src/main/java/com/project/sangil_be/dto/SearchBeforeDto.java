package com.project.sangil_be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchBeforeDto {

    private List<MountainInfo> mountainLists;

    @Getter
    @NoArgsConstructor
    public static class MountainInfo {
        private Long mountain100Id;
        private String mountain;
        private String mountainAddress;
        private String mountainImgUrl;
        private float starAvr;
        private String lat;
        private String lng;

        public MountainInfo(Long mountain100Id, String mountain, String mountainAddress, String mountainImgUrl, Float starAvr, String lat, String lng) {
            this.mountain100Id = mountain100Id;
            this.mountain = mountain;
            this.mountainAddress = mountainAddress;
            this.mountainImgUrl = mountainImgUrl;
            this.starAvr = starAvr;
            this.lat = lat;
            this.lng = lng;
        }
    }
}
